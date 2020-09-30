package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.GastoPeriodoResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.GastoTotalResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResumoCartaoResponse;
import br.com.intelector.gerenciarcontapagar.domain.*;
import br.com.intelector.gerenciarcontapagar.model.Gasto;
import br.com.intelector.gerenciarcontapagar.repository.GastoRepository;
import br.com.intelector.gerenciarcontapagar.repository.dto.LancamentoConsolidadoDTO;
import br.com.intelector.gerenciarcontapagar.repository.dto.PeriodoValorDTO;
import br.com.intelector.gerenciarcontapagar.repository.dto.ValorResponsavelDTO;
import br.com.intelector.gerenciarcontapagar.utils.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class GastoService extends AbstractService{

    private final GastoRepository repository;
    private final LancamentoService lancamentoService;
    private final ContaRecorrenteService contaRecorrenteService;
    private final ModelMapperUtils modelMapperUtils;

    public GastoService(GastoRepository repository,
                        LancamentoService lancamentoService,
                        ContaRecorrenteService contaRecorrenteService,
                        ModelMapperUtils modelMapperUtils) {
        this.repository = repository;
        this.lancamentoService = lancamentoService;
        this.contaRecorrenteService = contaRecorrenteService;
        this.modelMapperUtils = modelMapperUtils;
    }

    public ResumoCartaoResponse resumoCartao(DominioCartao cartao, DominioResponsavel responsavel) {
        ResumoCartaoResponse response = new ResumoCartaoResponse();
        List<DominioResponsavel> responsaveis = getResponsaveis(responsavel);

        BigDecimal valorAtual = valorTotal(cartao, responsaveis, false);
        response.setValorAtual(valorAtual.setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal valorProjecao = valorTotal(cartao, responsaveis, true);
        response.setValorProjecao(valorProjecao.setScale(2, RoundingMode.HALF_EVEN));
        return response;
    }

    public GastoTotalResponse totalGasto(DominioResponsavel responsavel) {
        BigDecimal valorTotalAtual = valorTotal(null, getResponsaveis(responsavel),  false);
        BigDecimal valorTotalProjecao = valorTotal(null, getResponsaveis(responsavel),  true);
        return new GastoTotalResponse(valorTotalAtual, valorTotalProjecao);
    }

    private BigDecimal valorTotal(DominioCartao cartao,
                                  List<DominioResponsavel> responsaveis,
                                  Boolean projecao) {
        return Optional.ofNullable(repository.valorTotal(cartao, responsaveis, projecao)).orElse(new BigDecimal(0));
    }

    public List<GastoPeriodoResponse> listByResponsavel(DominioResponsavel responsavel) {
        List<PeriodoValorDTO> periodoValorDTOS;
        List<DominioResponsavel> responsaveis = getResponsaveis(responsavel);

        periodoValorDTOS = repository.consolidadoPeriodo(responsaveis, DominioSituacaoRegistro.ATIVO, false);

        return periodoValorDTOS
                .stream()
                .map(gasto -> new GastoPeriodoResponse(gasto.getPeriodo(), gasto.getValor()))
                .collect(Collectors.toList());

    }

    @Transactional
    public void consolidarGasto(String periodo){
        consolidarGastoByDespesaCasa(periodo);
        consolidarGastoByLancamento(periodo);
    }

    public void consolidarGastoByLancamento(String periodo) {
        consolidarGastoByLancamentoAtual(periodo);
        consolidarGastoByLancamentoFuturo(periodo);
    }

    public void consolidarGastoByDespesaCasa(String periodo) {
        List<ValorResponsavelDTO> listDespesaCasa = contaRecorrenteService.valorTotal(DominioCategoriaContaRecorrente.DESPESA_CASA);
        for (ValorResponsavelDTO despesaCasa : listDespesaCasa) {
            log.info("Processando Despesa Casa {} {}", despesaCasa.getResponsavel(), despesaCasa.getValor());
            Gasto gastoAtual = new Gasto();
            Gasto gastoProjecao = new Gasto();

            gastoAtual.setPeriodo(periodo);
            gastoAtual.setTipoGasto(DominioTipoGasto.DESPESA_CASA);
            gastoAtual.setValor(despesaCasa.getValor());
            gastoAtual.setResponsavel(despesaCasa.getResponsavel());
            gastoAtual.setCdUsuAtu(USUARIO_ATUALIZACAO);

            modelMapperUtils.copy(gastoAtual, gastoProjecao);

            gastoAtual.setProjecao(false);
            repository.save(gastoAtual);

            gastoAtual.setProjecao(true);
            repository.save(gastoProjecao);
        }
    }

    private void consolidarGastoByLancamentoAtual(String periodo) {
        List<LancamentoConsolidadoDTO> listLancamentoConsolidado = lancamentoService.lancamentoConsolidadoAtual();
        for (LancamentoConsolidadoDTO consolidadoDTO : listLancamentoConsolidado) {
            log.info("Processando Lancamento Atual {} {} {}", consolidadoDTO.getResponsavel(), consolidadoDTO.getCartao(), consolidadoDTO.getValor());
            salvarByLancamentoConsolidado(periodo, consolidadoDTO, false);
        }
    }

    private void consolidarGastoByLancamentoFuturo(String periodo) {
        List<LancamentoConsolidadoDTO> listLancamentoConsolidado = lancamentoService.lancamentoConsolidadoFuturo();
        for (LancamentoConsolidadoDTO consolidadoDTO : listLancamentoConsolidado) {
            log.info("Processando Lancamento Futuro {} {} {}", consolidadoDTO.getResponsavel(), consolidadoDTO.getCartao(), consolidadoDTO.getValor());
            salvarByLancamentoConsolidado(periodo, consolidadoDTO, true);
        }
    }

    private void salvarByLancamentoConsolidado(String periodo, LancamentoConsolidadoDTO consolidadoDTO, boolean projecao) {
        Gasto gasto = new Gasto();
        gasto.setCartao(consolidadoDTO.getCartao());
        gasto.setPeriodo(periodo);
        gasto.setTipoGasto(DominioTipoGasto.CARTAO);
        gasto.setValor(consolidadoDTO.getValor());
        gasto.setResponsavel(consolidadoDTO.getResponsavel());
        gasto.setProjecao(projecao);
        gasto.setCdUsuAtu(USUARIO_ATUALIZACAO);
        repository.save(gasto);
    }

    private List<DominioResponsavel> getResponsaveis(DominioResponsavel responsavel){
        List<DominioResponsavel> responsaveis = new ArrayList<>();
        responsaveis.add(responsavel);

        if (responsavel == DominioResponsavel.THIAGO || responsavel == DominioResponsavel.BRENDA)
            responsaveis.add(DominioResponsavel.COMPARTILHADO);
        return responsaveis;
    }


}
