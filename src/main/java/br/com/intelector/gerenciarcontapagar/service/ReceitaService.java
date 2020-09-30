package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.ReceitaResponse;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.exception.RegistroInexistenteException;
import br.com.intelector.gerenciarcontapagar.model.Receita;
import br.com.intelector.gerenciarcontapagar.repository.ReceitaRepository;
import br.com.intelector.gerenciarcontapagar.utils.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ReceitaService {

    private final ReceitaRepository repository;
    private final ModelMapperUtils modelMapperUtils;

    public ReceitaService(ReceitaRepository repository,
                          ModelMapperUtils modelMapperUtils) {
        this.repository = repository;
        this.modelMapperUtils = modelMapperUtils;
    }

    public ReceitaResponse search(DominioResponsavel responsavel) throws RegistroInexistenteException {
        Receita receita = repository.findByResponsavel(responsavel).orElseThrow(RegistroInexistenteException::new);
        return modelMapperUtils.map(receita, ReceitaResponse.class);
    }
}
