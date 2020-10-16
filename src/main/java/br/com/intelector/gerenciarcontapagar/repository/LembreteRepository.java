package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;
import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import br.com.intelector.gerenciarcontapagar.repository.dto.QuantidadeValorDTO;
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

    Optional<Lembrete> findByDataCompraAndValorAndProcessado(Date dataCompra, BigDecimal valor, boolean processado);

    @Query("SELECT l FROM Lembrete l " +
            "WHERE l.situacao = :situacao " +
            "AND (:search is null or LOWER(l.observacao) LIKE %:search%) " +
            "ORDER BY l.dataCompra DESC ")
    Page<Lembrete> search(String search, DominioSituacaoRegistro situacao, Pageable pageable);

    @Query("SELECT COUNT(l) AS quantidade, SUM(l.valor) AS valor " +
            "FROM Lembrete l " +
            "WHERE l.processado = :processado " +
            "AND l.responsavel = :responsavel ")
    QuantidadeValorDTO consolidado(DominioResponsavel responsavel, Boolean processado);

}
