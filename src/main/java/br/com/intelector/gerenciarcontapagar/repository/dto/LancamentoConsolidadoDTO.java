package br.com.intelector.gerenciarcontapagar.repository.dto;

import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;

import java.math.BigDecimal;

public interface LancamentoConsolidadoDTO {
    DominioCartao getCartao();
    DominioResponsavel getResponsavel();
    BigDecimal getValor();
}
