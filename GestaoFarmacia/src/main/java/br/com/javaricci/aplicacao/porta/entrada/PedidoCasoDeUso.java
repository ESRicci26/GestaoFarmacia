package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.dominio.modelo.Carrinho;
import br.com.javaricci.dominio.modelo.ItemPedido;
import br.com.javaricci.dominio.modelo.Pedido;
import java.math.BigDecimal;
import java.util.List;

/** Casos de uso do carrinho e dos pedidos de uma loja. */
public interface PedidoCasoDeUso {

    void adicionarItemAoCarrinho(Carrinho carrinho, Long medicamentoId, int quantidade, BigDecimal precoVenda);

    void alterarQuantidadeNoCarrinho(Carrinho carrinho, Long medicamentoId, int novaQuantidade);

    /** Confirma o carrinho como pedido e baixa o estoque. Retorna o número do pedido criado. */
    Long finalizarPedido(Long lojaId, Carrinho carrinho);

    List<Pedido> listarPedidosDaLoja(Long lojaId);

    Pedido buscarPedido(Long lojaId, Long pedidoId);

    ItemPedido buscarItemDoPedido(Long pedidoId, Long itemId);
}
