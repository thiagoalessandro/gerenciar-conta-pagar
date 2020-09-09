package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.GastoPeriodoResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResumoCartaoResponse;
import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;
import br.com.intelector.gerenciarcontapagar.domain.DominioTipoGasto;
import br.com.intelector.gerenciarcontapagar.exception.DominioException;
import br.com.intelector.gerenciarcontapagar.model.Gasto;
import br.com.intelector.gerenciarcontapagar.repository.GastoRepository;
import br.com.intelector.gerenciarcontapagar.repository.dto.LancamentoConsolidadoDTO;
import br.com.intelector.gerenciarcontapagar.repository.dto.PeriodoGastoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class GastoService extends AbstractService{

    private final GastoRepository repository;
    private final LancamentoService lancamentoService;

    public GastoService(GastoRepository repository,
                        LancamentoService lancamentoService) {
        this.repository = repository;
        this.lancamentoService = lancamentoService;
    }

    public ResumoCartaoResponse resumoCartaoByTipoAndResponsavel(DominioCartao cartao, DominioResponsavel responsavel) {
        ResumoCartaoResponse response = new ResumoCartaoResponse();
        List<DominioResponsavel> responsaveis = getResponsaveis(responsavel);

        BigDecimal valorAtual = valorByCartaoAndResponsavel(cartao, responsaveis, false);
        response.setValorAtual(valorAtual.setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal valorProjecao = valorByCartaoAndResponsavel(cartao, responsaveis, true);
        response.setValorProjecao(valorProjecao.setScale(2, RoundingMode.HALF_EVEN));
        return response;
    }

    private BigDecimal valorByCartaoAndResponsavel(DominioCartao cartao, List<DominioResponsavel> responsaveis, boolean projecao) {
        return Optional.ofNullable(repository.valorByCartaoAndResponsavel(cartao, responsaveis, projecao)).orElse(new BigDecimal(0));
    }

    public List<GastoPeriodoResponse> listByResponsavel(DominioResponsavel responsavel) {
        List<PeriodoGastoDTO> periodoGastoDTOS;
        List<DominioResponsavel> responsaveis = getResponsaveis(responsavel);

        periodoGastoDTOS = repository.findByResposaveis(responsaveis, DominioSituacaoRegistro.ATIVO);

        return periodoGastoDTOS
                .stream()
                .map(gasto -> new GastoPeriodoResponse(gasto.getPeriodo(), gasto.getValor()))
                .collect(Collectors.toList());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void consolidarGastoByLancamento(String periodo) throws DominioException {
        consolidarGastoByLancamentoAtual(periodo);
        consolidarGastoByLancamentoFuturo(periodo);
    }

    private void consolidarGastoByLancamentoAtual(String periodo) throws DominioException {
        List<LancamentoConsolidadoDTO> lancamentoConsolidadoDTOList = lancamentoService.lancamentoConsolidadoAtual();
        for (LancamentoConsolidadoDTO consolidadoDTO : lancamentoConsolidadoDTOList) {
            log.info("Processando Lancamento Atual{} {} {}", consolidadoDTO.getResponsavel(), consolidadoDTO.getCartao(), consolidadoDTO.getValor());
            salvarByLancamentoConsolidado(periodo, consolidadoDTO, false);
        }
    }

    private void consolidarGastoByLancamentoFuturo(String periodo) throws DominioException {
        List<LancamentoConsolidadoDTO> lancamentoConsolidadoDTOList = lancamentoService.lancamentoConsolidadoFuturo();
        for (LancamentoConsolidadoDTO consolidadoDTO : lancamentoConsolidadoDTOList) {
            log.info("Processando Lancamento Futuro{} {} {}", consolidadoDTO.getResponsavel(), consolidadoDTO.getCartao(), consolidadoDTO.getValor());
            salvarByLancamentoConsolidado(periodo, consolidadoDTO, true);
        }
    }

    private void salvarByLancamentoConsolidado(String periodo, LancamentoConsolidadoDTO consolidadoDTO, boolean projecao) throws DominioException {
        Gasto gasto = new Gasto();
        gasto.setCartao(DominioCartao.convertStringToEnum(consolidadoDTO.getCartao()));
        gasto.setPeriodo(periodo);
        gasto.setTipoGasto(DominioTipoGasto.CARTAO);
        gasto.setValor(consolidadoDTO.getValor());
        gasto.setResponsavel(DominioResponsavel.valueOf(consolidadoDTO.getResponsavel()));
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
