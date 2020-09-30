package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaContaRecorrente;
import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.model.ContaRecorrente;
import br.com.intelector.gerenciarcontapagar.repository.dto.QuantidadeValorDTO;
import br.com.intelector.gerenciarcontapagar.repository.dto.ValorResponsavelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRecorrenteRepository extends JpaRepository<ContaRecorrente, Long> {

    Optional<ContaRecorrente> findByDescricaoIgnoreCase(String descricao);

    @Query("SELECT COUNT(c) AS quantidade, SUM(c.valor) AS valor " +
            "FROM ContaRecorrente c " +
            "WHERE c.categoria = :categoria " +
            "AND c.responsavel = :responsavel ")
    QuantidadeValorDTO consolidado(DominioResponsavel responsavel, DominioCategoriaContaRecorrente categoria);

    @Query("SELECT c.responsavel AS responsavel, SUM(c.valor) AS valor " +
            "FROM ContaRecorrente c " +
            "WHERE c.categoria = :categoria " +
            "GROUP BY c.responsavel")
    List<ValorResponsavelDTO> valorTotal(DominioCategoriaContaRecorrente categoria);
}
