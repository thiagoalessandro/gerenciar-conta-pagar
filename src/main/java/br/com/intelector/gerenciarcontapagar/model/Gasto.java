package br.com.intelector.gerenciarcontapagar.model;

import br.com.intelector.gerenciarcontapagar.domain.*;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "tbl_gasto")
public class Gasto extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "periodo")
    private String periodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_gasto")
    private DominioTipoGasto tipoGasto;

    @Enumerated(EnumType.STRING)
    @Column(name = "cartao")
    private DominioCartao cartao;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel")
    private DominioResponsavel responsavel;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "projecao")
    private Boolean projecao;

}
