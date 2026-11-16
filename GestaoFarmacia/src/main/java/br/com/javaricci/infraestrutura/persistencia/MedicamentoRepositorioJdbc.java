package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.MedicamentoRepositorio;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Medicamento;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/** Persistência de medicamentos com JdbcTemplate (PostgreSQL). */
@Repository
public class MedicamentoRepositorioJdbc implements MedicamentoRepositorio {

    private static final String COLUNAS = "id, farmacia_id, nome, tipo, composicao, descricao, "
            + "quantidade_disponivel, preco, data_fabricacao, data_validade";

    private static final String SQL_INSERIR =
            "INSERT INTO medicamentos (farmacia_id, nome, tipo, composicao, descricao, "
                    + "quantidade_disponivel, preco, data_fabricacao, data_validade) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String SQL_ATUALIZAR =
            "UPDATE medicamentos SET nome = ?, tipo = ?, composicao = ?, descricao = ?, "
                    + "quantidade_disponivel = ?, preco = ?, data_fabricacao = ?, data_validade = ? "
                    + "WHERE id = ?";
    private static final String SQL_EXCLUIR = "DELETE FROM medicamentos WHERE id = ?";
    private static final String SQL_LISTAR = "SELECT " + COLUNAS + " FROM medicamentos ORDER BY nome";
    private static final String SQL_LISTAR_POR_FARMACIA =
            "SELECT " + COLUNAS + " FROM medicamentos WHERE farmacia_id = ? ORDER BY nome";
    private static final String SQL_LISTAR_POR_NOME =
            "SELECT " + COLUNAS + " FROM medicamentos WHERE nome ILIKE ? ORDER BY nome";
    private static final String SQL_BUSCAR_POR_ID =
            "SELECT " + COLUNAS + " FROM medicamentos WHERE id = ?";
    private static final String SQL_REDUZIR_ESTOQUE =
            "UPDATE medicamentos SET quantidade_disponivel = quantidade_disponivel - ? "
                    + "WHERE id = ? AND quantidade_disponivel >= ?";

    private static final RowMapper<Medicamento> MAPEADOR = (rs, linha) -> new Medicamento(
            rs.getLong("id"),
            rs.getLong("farmacia_id"),
            rs.getString("nome"),
            rs.getString("tipo"),
            rs.getString("composicao"),
            rs.getString("descricao"),
            rs.getInt("quantidade_disponivel"),
            rs.getBigDecimal("preco"),
            rs.getObject("data_fabricacao", LocalDate.class),
            rs.getObject("data_validade", LocalDate.class));

    private final JdbcTemplate jdbcTemplate;

    public MedicamentoRepositorioJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long inserir(Medicamento medicamento) {
        return jdbcTemplate.queryForObject(SQL_INSERIR, Long.class,
                medicamento.getFarmaciaId(),
                medicamento.getNome(),
                medicamento.getTipo(),
                medicamento.getComposicao(),
                medicamento.getDescricao(),
                medicamento.getQuantidadeDisponivel(),
                medicamento.getPreco(),
                medicamento.getDataFabricacao(),
                medicamento.getDataValidade());
    }

    @Override
    public boolean atualizar(Medicamento medicamento) {
        return jdbcTemplate.update(SQL_ATUALIZAR,
                medicamento.getNome(),
                medicamento.getTipo(),
                medicamento.getComposicao(),
                medicamento.getDescricao(),
                medicamento.getQuantidadeDisponivel(),
                medicamento.getPreco(),
                medicamento.getDataFabricacao(),
                medicamento.getDataValidade(),
                medicamento.getId()) > 0;
    }

    @Override
    public boolean excluir(Long id) {
        try {
            return jdbcTemplate.update(SQL_EXCLUIR, id) > 0;
        } catch (DataIntegrityViolationException excecao) {
            throw new RegraNegocioException("Não é possível excluir: o medicamento já consta em pedidos.");
        }
    }

    @Override
    public List<Medicamento> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public List<Medicamento> listarPorFarmacia(Long farmaciaId) {
        return jdbcTemplate.query(SQL_LISTAR_POR_FARMACIA, MAPEADOR, farmaciaId);
    }

    @Override
    public List<Medicamento> listarPorNome(String termo) {
        return jdbcTemplate.query(SQL_LISTAR_POR_NOME, MAPEADOR, "%" + termo + "%");
    }

    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR, id).stream().findFirst();
    }

    @Override
    public boolean reduzirEstoque(Long medicamentoId, int quantidade) {
        return jdbcTemplate.update(SQL_REDUZIR_ESTOQUE, quantidade, medicamentoId, quantidade) == 1;
    }
}
