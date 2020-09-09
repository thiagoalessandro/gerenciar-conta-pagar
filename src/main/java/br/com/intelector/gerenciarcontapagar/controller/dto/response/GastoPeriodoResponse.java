package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GastoPeriodoResponse implements Serializable {

    private String periodo;
    private BigDecimal valor;

    public GastoPeriodoResponse(String periodo, BigDecimal valor) {
        this.periodo = periodo;
        this.valor = valor;
    }

}
