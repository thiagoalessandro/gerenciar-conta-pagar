package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;
import br.com.intelector.gerenciarcontapagar.model.Gasto;
import br.com.intelector.gerenciarcontapagar.repository.dto.PeriodoGastoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    @Query("SELECT g.periodo AS periodo, SUM(g.valor) AS valor FROM Gasto g " +
            "WHERE g.situacao = :situacao " +
            "AND g.responsavel in(:responsaveis) " +
            "GROUP BY g.periodo " +
            "ORDER BY g.periodo ASC")
    List<PeriodoGastoDTO> findByResposaveis(@Param("responsaveis") List<DominioResponsavel> responsaveis,
                                            @Param("situacao") DominioSituacaoRegistro situacao);


    @Query("SELECT SUM(g.valor) FROM Gasto g " +
            "WHERE g.responsavel in(:responsaveis) " +
            "AND g.cartao = :cartao " +
            "AND g.periodo IN ( SELECT MAX(g2.periodo) FROM Gasto g2 ) "+
            "AND g.projecao = :projecao " +
            "GROUP BY g.periodo " +
            "ORDER BY g.periodo DESC")
    BigDecimal valorByCartaoAndResponsavel(@Param("cartao") DominioCartao cartao,
                                                @Param("responsaveis") List<DominioResponsavel> responsaveis,
                                                @Param("projecao") Boolean projecao);
}
