package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.controller.dto.request.LembreteRequest;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.LembreteResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResponsavelResponse;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;
import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import br.com.intelector.gerenciarcontapagar.repository.LembreteRepository;
import br.com.intelector.gerenciarcontapagar.utils.ApplicationUtils;
import br.com.intelector.gerenciarcontapagar.utils.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ResponsavelService {

    private final ModelMapperUtils modelMapperUtils;
    private final ApplicationUtils applicationUtils;


    public ResponsavelService(ModelMapperUtils modelMapperUtils,
                              ApplicationUtils applicationUtils) {
        this.modelMapperUtils = modelMapperUtils;
        this.applicationUtils = applicationUtils;
    }

    public List<ResponsavelResponse> list() {
        return Arrays.stream(DominioResponsavel.values())
                .map(responsavel -> new ResponsavelResponse(responsavel.name()))
                .collect(Collectors.toList());
    }

}
