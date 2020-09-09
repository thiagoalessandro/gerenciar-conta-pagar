package br.com.intelector.gerenciarcontapagar.controller.dto.request;

import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LembreteRequest {

    private Long id;

    @NotNull(message = "Observação é obrigatória")
    private String observacao;

    @NotNull(message = "Data da compra é obrigatória")
    private Long dataCompra;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "Responsável é obrigatório")
    private DominioResponsavel responsavel;

}
