package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ResumoLembreteResponse implements Serializable {

    private Integer quantidade;
    private BigDecimal valor;

}
