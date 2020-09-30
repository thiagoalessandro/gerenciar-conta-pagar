package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ResumoDespesaResponse implements Serializable {

    private Integer quantidade;
    private BigDecimal valor;

}
