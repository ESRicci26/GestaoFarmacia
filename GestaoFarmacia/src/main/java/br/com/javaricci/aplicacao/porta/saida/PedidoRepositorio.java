package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.ItemPedido;
import br.com.javaricci.dominio.modelo.Pedido;
import java.util.List;
import java.util.Optional;

/** Porta de saída para persistência de pedidos e seus itens. */
public interface PedidoRepositorio {

    Long inserir(Pedido pedido);

    Long inserirItem(Long pedidoId, ItemPedido item);

    List<Pedido> listarPorLoja(Long lojaId);

    List<ItemPedido> listarItensPorPedido(Long pedidoId);

    Optional<Pedido> buscarPorId(Long id);

    Optional<ItemPedido> buscarItemPorId(Long itemId);
}
