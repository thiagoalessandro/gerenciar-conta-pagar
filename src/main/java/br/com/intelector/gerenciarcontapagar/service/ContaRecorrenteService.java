package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResumoDespesaResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResumoLembreteResponse;
import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaContaRecorrente;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.model.ContaRecorrente;
import br.com.intelector.gerenciarcontapagar.repository.ContaRecorrenteRepository;
import br.com.intelector.gerenciarcontapagar.repository.dto.QuantidadeValorDTO;
import br.com.intelector.gerenciarcontapagar.repository.dto.ValorResponsavelDTO;
import br.com.intelector.gerenciarcontapagar.utils.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ContaRecorrenteService {

    private final ContaRecorrenteRepository repository;
    private final ModelMapperUtils modelMapperUtils;

    public ContaRecorrenteService(ContaRecorrenteRepository repository,
                                  ModelMapperUtils modelMapperUtils) {
        this.repository = repository;
        this.modelMapperUtils = modelMapperUtils;
    }

    public Optional<ContaRecorrente> findByDescricao(String descricao) {
        return repository.findByDescricaoIgnoreCase(descricao);
    }

    public List<ValorResponsavelDTO> valorTotal(DominioCategoriaContaRecorrente categoriaContaRecorrente) {
        return repository.valorTotal(categoriaContaRecorrente);
    }

    public Optional<ContaRecorrente> searchContaRecorrente(String descricao) {
        return findByDescricao(descricao);
    }

    public ResumoDespesaResponse resumoContaRecorrente(DominioResponsavel responsavel, DominioCategoriaContaRecorrente categoriaContaRecorrente) {
        QuantidadeValorDTO quantidadeValorDTO = repository.consolidado(responsavel, categoriaContaRecorrente);
        return modelMapperUtils.map(quantidadeValorDTO, ResumoDespesaResponse.class);
    }
}
