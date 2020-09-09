package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LembreteResponse implements Serializable {

    private Long id;
    private String observacao;
    private Long dataCompra;
    private BigDecimal valor;
    private DominioResponsavel responsavel;
    private String urlImagemResponsavel;
    private DominioCategoriaLancamento categoria;

}
