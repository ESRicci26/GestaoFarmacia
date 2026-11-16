package br.com.javaricci.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Pedido realizado por uma loja, composto por um ou mais itens. */
public class Pedido {

    private final Long id;
    private final Long lojaId;
    private final LocalDateTime criadoEm;
    private final List<ItemPedido> itens = new ArrayList<>();

    public Pedido(Long id, Long lojaId, LocalDateTime criadoEm) {
        this.id = id;
        this.lojaId = lojaId;
        this.criadoEm = criadoEm;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void definirItens(List<ItemPedido> novosItens) {
        itens.clear();
        itens.addAll(novosItens);
    }

    public BigDecimal getTotal() {
        return itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() {
        return id;
    }

    public Long getLojaId() {
        return lojaId;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
