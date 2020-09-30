package br.com.intelector.gerenciarcontapagar.model;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_receita")
public class Receita extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel")
    private DominioResponsavel responsavel;

}
