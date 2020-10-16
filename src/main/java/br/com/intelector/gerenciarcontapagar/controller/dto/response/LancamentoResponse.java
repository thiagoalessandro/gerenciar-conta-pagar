package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import br.com.intelector.gerenciarcontapagar.domain.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LancamentoResponse implements Serializable {

    private Long id;
    private Date dataCompra;
    private String descricao;
    private Integer qtdParcela;
    private Integer qtdParcelaRestante;
    private Integer qtdParcelaAtual;
    private BigDecimal valor;
    private DominioResponsavel responsavel;
    private DominioCartao cartao;

}
