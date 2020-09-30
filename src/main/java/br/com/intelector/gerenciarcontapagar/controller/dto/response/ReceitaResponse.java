package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ReceitaResponse implements Serializable {

    private Long id;
    private BigDecimal valor;
    private DominioResponsavel responsavel;

}
