package br.com.javaricci.dominio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

/** Item temporário do carrinho, ainda não confirmado como pedido. */
public class ItemCarrinho implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long medicamentoId;
    private final String nomeMedicamento;
    private final BigDecimal precoVenda;
    private int quantidade;

    public ItemCarrinho(Long medicamentoId, String nomeMedicamento, int quantidade, BigDecimal precoVenda) {
        this.medicamentoId = medicamentoId;
        this.nomeMedicamento = nomeMedicamento;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public BigDecimal getSubtotal() {
        return precoVenda.multiply(BigDecimal.valueOf(quantidade));
    }

    public void somarQuantidade(int adicional) {
        this.quantidade += adicional;
    }

    public void definirQuantidade(int novaQuantidade) {
        this.quantidade = novaQuantidade;
    }

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
