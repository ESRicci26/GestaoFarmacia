package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.FarmaciaRepositorio;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Farmacia;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/** Persistência de farmácias com JdbcTemplate (PostgreSQL). */
@Repository
public class FarmaciaRepositorioJdbc implements FarmaciaRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO farmacias (nome, localizacao) VALUES (?, ?) RETURNING id";
    private static final String SQL_ATUALIZAR =
            "UPDATE farmacias SET nome = ?, localizacao = ? WHERE id = ?";
    private static final String SQL_EXCLUIR = "DELETE FROM farmacias WHERE id = ?";
    private static final String SQL_LISTAR =
            "SELECT id, nome, localizacao FROM farmacias ORDER BY nome";
    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id, nome, localizacao FROM farmacias WHERE id = ?";

    private static final RowMapper<Farmacia> MAPEADOR = (rs, linha) ->
            new Farmacia(rs.getLong("id"), rs.getString("nome"), rs.getString("localizacao"));

    private final JdbcTemplate jdbcTemplate;

    public FarmaciaRepositorioJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long inserir(Farmacia farmacia) {
        return jdbcTemplate.queryForObject(SQL_INSERIR, Long.class,
                farmacia.getNome(), farmacia.getLocalizacao());
    }

    @Override
    public boolean atualizar(Farmacia farmacia) {
        return jdbcTemplate.update(SQL_ATUALIZAR,
                farmacia.getNome(), farmacia.getLocalizacao(), farmacia.getId()) > 0;
    }

    @Override
    public boolean excluir(Long id) {
        try {
            return jdbcTemplate.update(SQL_EXCLUIR, id) > 0;
        } catch (DataIntegrityViolationException excecao) {
            throw new RegraNegocioException(
                    "Não é possível excluir: há medicamentos desta farmácia vinculados a pedidos.");
        }
    }

    @Override
    public List<Farmacia> listar() {
        return jdbcTemplate.query(SQL_LISTAR, MAPEADOR);
    }

    @Override
    public Optional<Farmacia> buscarPorId(Long id) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR, id).stream().findFirst();
    }
}
