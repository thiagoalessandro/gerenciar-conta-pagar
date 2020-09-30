package br.com.intelector.gerenciarcontapagar.repository.dto;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;

import java.math.BigDecimal;

public interface ValorResponsavelDTO {
    DominioResponsavel getResponsavel();
    BigDecimal getValor();
}
