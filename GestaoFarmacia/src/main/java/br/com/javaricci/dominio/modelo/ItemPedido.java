package br.com.javaricci.dominio.modelo;

import java.math.BigDecimal;

/** Item de um pedido: medicamento, quantidade e preço de venda praticado pela loja. */
public class ItemPedido {

    /** Quantidades iguais ou inferiores a este limite sinalizam necessidade de reabastecimento. */
    public static final int LIMITE_ESTOQUE_BAIXO = 10;

    private final Long id;
    private final Long pedidoId;
    private final Long medicamentoId;
    private final String nomeMedicamento;
    private final int quantidade;
    private final BigDecimal precoVenda;

    public ItemPedido(Long id, Long pedidoId, Long medicamentoId, String nomeMedicamento,
                      int quantidade, BigDecimal precoVenda) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.medicamentoId = medicamentoId;
        this.nomeMedicamento = nomeMedicamento;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public BigDecimal getSubtotal() {
        return precoVenda.multiply(BigDecimal.valueOf(quantidade));
    }

    public boolean isEstoqueSuficiente() {
        return quantidade > LIMITE_ESTOQUE_BAIXO;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }
}
