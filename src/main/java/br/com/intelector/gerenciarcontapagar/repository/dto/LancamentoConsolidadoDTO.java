package br.com.intelector.gerenciarcontapagar.repository.dto;

import lombok.ToString;

import java.math.BigDecimal;

public interface LancamentoConsolidadoDTO {
    String getCartao();
    String getResponsavel();
    BigDecimal getValor();
}
