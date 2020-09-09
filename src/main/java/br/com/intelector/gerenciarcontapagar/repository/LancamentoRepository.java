package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.model.Lancamento;
import br.com.intelector.gerenciarcontapagar.repository.dto.LancamentoConsolidadoDTO;
import br.com.intelector.gerenciarcontapagar.utils.ModelMapperUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findBySituacaoLancamentoAndCartao(DominioSituacaoLancamento dominioSituacaoLancamento, DominioCartao cartao);

    Optional<Lancamento> findByHashAndSituacaoLancamento(String hash, DominioSituacaoLancamento dominioSituacaoLancamento);

    boolean existsByHash(String hash);

    @Query(value = "SELECT cartao, responsavel, SUM(valor) valor " +
            "FROM (SELECT cartao, " +
            "             responsavel, " +
            "             CASE responsavel " +
            "                 WHEN 'COMPARTILHADO' then valor / 2 " +
            "                 ELSE valor " +
            "                 END valor " +
            "      FROM tbl_lancamento " +
            "      WHERE 1=1 " +
            "        AND sit_lancamento = 'PENDENTE' " +
            "     ) AS x " +
            "GROUP BY cartao, responsavel", nativeQuery = true)
    List<LancamentoConsolidadoDTO> lancamentoConsolidadoAtual();

    @Query(value = "SELECT cartao, responsavel, SUM(valor) valor " +
            "FROM (SELECT cartao, " +
            "             responsavel, " +
            "             CASE responsavel " +
            "                 WHEN 'COMPARTILHADO' then valor / 2 " +
            "                 ELSE valor " +
            "                 END valor " +
            "      FROM tbl_lancamento " +
            "      WHERE 1 = 1 " +
            "        AND sit_lancamento = 'PENDENTE' " +
            "        AND qtd_parcela_restante = 0 " +
            "      UNION " +
            "      SELECT cartao, responsavel, valor " +
            "      FROM tbl_conta_recorrente " +
            "      WHERE 1 = 1 " +
            "        AND categoria = 'ASSINATURA') AS x " +
            "GROUP BY cartao, responsavel", nativeQuery = true)
    List<LancamentoConsolidadoDTO> lancamentoConsolidadoFuturo();


}
