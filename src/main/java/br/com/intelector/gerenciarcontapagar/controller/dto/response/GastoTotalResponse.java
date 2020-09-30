package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GastoTotalResponse implements Serializable {

    private BigDecimal valorAtual;
    private BigDecimal valorProjecao;

    public GastoTotalResponse(BigDecimal valorAtual, BigDecimal valorProjecao) {
        this.valorAtual = valorAtual;
        this.valorProjecao = valorProjecao;
    }
}
