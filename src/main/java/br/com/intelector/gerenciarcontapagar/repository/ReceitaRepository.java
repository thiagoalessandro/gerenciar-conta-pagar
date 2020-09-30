package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.model.Receita;
import br.com.intelector.gerenciarcontapagar.repository.dto.QuantidadeValorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {


    Optional<Receita> findByResponsavel(DominioResponsavel responsavel);
}
