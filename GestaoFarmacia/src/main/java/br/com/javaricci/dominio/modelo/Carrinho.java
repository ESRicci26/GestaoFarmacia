package br.com.javaricci.dominio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Carrinho de uma loja: reúne os itens escolhidos até a finalização do pedido. */
public class Carrinho implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Long, ItemCarrinho> itens = new LinkedHashMap<>();

    /** Adiciona o medicamento ou, se já existir no carrinho, soma a quantidade informada. */
    public void adicionarItem(Long medicamentoId, String nomeMedicamento, int quantidade, BigDecimal precoVenda) {
        ItemCarrinho existente = itens.get(medicamentoId);
        if (existente != null) {
            existente.somarQuantidade(quantidade);
            return;
        }
        itens.put(medicamentoId, new ItemCarrinho(medicamentoId, nomeMedicamento, quantidade, precoVenda));
    }

    public void definirQuantidade(Long medicamentoId, int novaQuantidade) {
        ItemCarrinho item = itens.get(medicamentoId);
        if (item != null) {
            item.definirQuantidade(novaQuantidade);
        }
    }

    public void removerItem(Long medicamentoId) {
        itens.remove(medicamentoId);
    }

    public void limpar() {
        itens.clear();
    }

    /** Quantidade do medicamento já reservada neste carrinho (0 se ausente). */
    public int quantidadeReservada(Long medicamentoId) {
        ItemCarrinho item = itens.get(medicamentoId);
        return item == null ? 0 : item.getQuantidade();
    }

    public boolean isVazio() {
        return itens.isEmpty();
    }

    public List<ItemCarrinho> getItens() {
        return new ArrayList<>(itens.values());
    }

    public BigDecimal getTotal() {
        return itens.values().stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
