package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;
import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {

    Optional<Lembrete> findByDataCompraAndValor(Date dataCompra, BigDecimal valor);

    @Query("SELECT l FROM Lembrete l " +
            "WHERE l.situacao = :situacao " +
            "AND (:search is null or LOWER(l.observacao) LIKE %:search%) " +
            "ORDER BY l.dataCompra DESC ")
    Page<Lembrete> findBySituacao(String search, DominioSituacaoRegistro situacao, Pageable pageable);

}
