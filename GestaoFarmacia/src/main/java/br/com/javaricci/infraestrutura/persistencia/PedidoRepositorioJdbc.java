package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.aplicacao.porta.saida.PedidoRepositorio;
import br.com.javaricci.dominio.modelo.ItemPedido;
import br.com.javaricci.dominio.modelo.Pedido;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/** Persistência de pedidos e itens com JdbcTemplate (PostgreSQL). */
@Repository
public class PedidoRepositorioJdbc implements PedidoRepositorio {

    private static final String SQL_INSERIR =
            "INSERT INTO pedidos (loja_id) VALUES (?) RETURNING id";
    private static final String SQL_INSERIR_ITEM =
            "INSERT INTO itens_pedido (pedido_id, medicamento_id, quantidade, preco_venda) "
                    + "VALUES (?, ?, ?, ?) RETURNING id";
    private static final String SQL_LISTAR_POR_LOJA =
            "SELECT id, loja_id, criado_em FROM pedidos WHERE loja_id = ? ORDER BY id DESC";
    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id, loja_id, criado_em FROM pedidos WHERE id = ?";
    private static final String SQL_ITENS_BASE =
            "SELECT i.id, i.pedido_id, i.medicamento_id, m.nome AS nome_medicamento, "
                    + "i.quantidade, i.preco_venda "
                    + "FROM itens_pedido i JOIN medicamentos m ON m.id = i.medicamento_id ";
    private static final String SQL_LISTAR_ITENS_POR_PEDIDO =
            SQL_ITENS_BASE + "WHERE i.pedido_id = ? ORDER BY i.id";
    private static final String SQL_BUSCAR_ITEM_POR_ID = SQL_ITENS_BASE + "WHERE i.id = ?";

    private static final RowMapper<Pedido> MAPEADOR_PEDIDO = (rs, linha) -> new Pedido(
            rs.getLong("id"),
            rs.getLong("loja_id"),
            rs.getObject("criado_em", LocalDateTime.class));

    private static final RowMapper<ItemPedido> MAPEADOR_ITEM = (rs, linha) -> new ItemPedido(
            rs.getLong("id"),
            rs.getLong("pedido_id"),
            rs.getLong("medicamento_id"),
            rs.getString("nome_medicamento"),
            rs.getInt("quantidade"),
            rs.getBigDecimal("preco_venda"));

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepositorioJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long inserir(Pedido pedido) {
        return jdbcTemplate.queryForObject(SQL_INSERIR, Long.class, pedido.getLojaId());
    }

    @Override
    public Long inserirItem(Long pedidoId, ItemPedido item) {
        return jdbcTemplate.queryForObject(SQL_INSERIR_ITEM, Long.class,
                pedidoId, item.getMedicamentoId(), item.getQuantidade(), item.getPrecoVenda());
    }

    @Override
    public List<Pedido> listarPorLoja(Long lojaId) {
        return jdbcTemplate.query(SQL_LISTAR_POR_LOJA, MAPEADOR_PEDIDO, lojaId);
    }

    @Override
    public List<ItemPedido> listarItensPorPedido(Long pedidoId) {
        return jdbcTemplate.query(SQL_LISTAR_ITENS_POR_PEDIDO, MAPEADOR_ITEM, pedidoId);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return jdbcTemplate.query(SQL_BUSCAR_POR_ID, MAPEADOR_PEDIDO, id).stream().findFirst();
    }

    @Override
    public Optional<ItemPedido> buscarItemPorId(Long itemId) {
        return jdbcTemplate.query(SQL_BUSCAR_ITEM_POR_ID, MAPEADOR_ITEM, itemId).stream().findFirst();
    }
}
