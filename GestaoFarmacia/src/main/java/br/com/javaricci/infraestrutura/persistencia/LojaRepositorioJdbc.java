package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.LojaRepositorio;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Loja;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/** Persistência de lojas com JdbcTemplate (PostgreSQL). */
@Repository
public class LojaRepositorioJdbc implements LojaRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO lojas (nome, localizacao) VALUES (?, ?) RETURNING id";
    private static final String SQL_ATUALIZAR =
            "UPDATE lojas SET nome = ?, localizacao = ? WHERE id = ?";
    private static final String SQL_EXCLUIR = "DELETE FROM lojas WHERE id = ?";
    private static final String SQL_LISTAR =
            "SELECT id, nome, localizacao FROM lojas ORDER BY nome";
    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id, nome, localizacao FROM lojas WHERE id = ?";

    private static final RowMapper<Loja> MAPEADOR = (rs, linha) ->
            new Loja(rs.getLong("id"), rs.getString("nome"), rs.getString("localizacao"));

    private final JdbcTemplate jdbcTemplate;

    public LojaRepositorioJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long inserir(Loja loja) {
        return jdbcTemplate.queryForObject(SQL_INSERIR, Long.class, loja.getNome(), loja.getLocalizacao());
    }

    @Override
    public boolean atualizar(Loja loja) {
        return jdbcTemplate.update(SQL_ATUALIZAR, loja.getNome(), loja.getLocalizacao(), loja.getId()) > 0;
    }

    @Override
    public boolean excluir(Long id) {
        try {
            return jdbcTemplate.update(SQL_EXCLUIR, id) > 0;
        } catch (DataIntegrityViolationException excecao) {
            throw new RegraNegocioException("Não é possível excluir a loja: existem registros vinculados a ela.");
        }
    }

    @Override
    public List<Loja> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public Optional<Loja> buscarPorId(Long id) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR, id).stream().findFirst();
    }
}
