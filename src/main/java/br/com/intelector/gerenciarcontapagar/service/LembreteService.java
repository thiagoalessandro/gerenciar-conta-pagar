package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.controller.dto.request.LembreteRequest;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.LembreteResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResumoLembreteResponse;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;
import br.com.intelector.gerenciarcontapagar.exception.RegistroInexistenteException;
import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import br.com.intelector.gerenciarcontapagar.repository.LembreteRepository;
import br.com.intelector.gerenciarcontapagar.repository.dto.QuantidadeValorDTO;
import br.com.intelector.gerenciarcontapagar.utils.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class LembreteService {

    private final LembreteRepository repository;
    private final ModelMapperUtils modelMapperUtils;


    public LembreteService(LembreteRepository repository,
                           ModelMapperUtils modelMapperUtils) {
        this.repository = repository;
        this.modelMapperUtils = modelMapperUtils;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LembreteResponse register(LembreteRequest request) {
        Lembrete lembrete =  modelMapperUtils.map(request, Lembrete.class);
        lembrete = repository.save(lembrete);
        return modelMapperUtils.map(lembrete, LembreteResponse.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LembreteResponse update(LembreteRequest request) throws RegistroInexistenteException {
        Lembrete lembrete = repository.findById(request.getId()).orElseThrow(RegistroInexistenteException::new);
        lembrete.setObservacao(request.getObservacao());
        lembrete.setDataCompra(new Date(request.getDataCompra()));
        lembrete.setValor(request.getValor());
        lembrete.setResponsavel(request.getResponsavel());
        lembrete = repository.save(lembrete);
        return modelMapperUtils.map(lembrete, LembreteResponse.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<LembreteResponse> list(String search, Integer page, Integer size) {
        search = search.equals("TODOS") ? null : search.toLowerCase();
        return repository.search(search, DominioSituacaoRegistro.ATIVO, PageRequest.of(page, size))
                .map(lembrete ->  modelMapperUtils.map(lembrete, LembreteResponse.class));
    }

    public void updateProcessado(Lembrete lembrete){
        lembrete.setProcessado(true);
        repository.saveAndFlush(lembrete);
    }

    public Optional<Lembrete> findByDataCompraAndValorAndProcessado(Date dataCompra, BigDecimal valor, boolean processado) {
        return repository.findByDataCompraAndValorAndProcessado(dataCompra, valor, processado);
    }

    public Optional<Lembrete> searchLembrete(Date dataCompra, BigDecimal valor, boolean processado) {
       return findByDataCompraAndValorAndProcessado(dataCompra, valor, processado);
    }

    public ResumoLembreteResponse resumoLembrete(DominioResponsavel responsavel, Boolean processado) {
        QuantidadeValorDTO quantidadeValorDTO = repository.consolidado(responsavel, processado);
        return modelMapperUtils.map(quantidadeValorDTO, ResumoLembreteResponse.class);
    }
}
