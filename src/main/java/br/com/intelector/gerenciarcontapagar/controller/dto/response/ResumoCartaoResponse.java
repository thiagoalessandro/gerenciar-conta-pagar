package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ResumoCartaoResponse implements Serializable {

    private BigDecimal valorAtual;
    private BigDecimal valorProjecao;

}
