package br.com.javaricci.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.javaricci.dominio.modelo.Carrinho;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CarrinhoTest {

    @Test
    void deveSomarQuantidadeAoAdicionarMesmoMedicamento() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionarItem(1L, "Crocin", 2, new BigDecimal("25.00"));
        carrinho.adicionarItem(1L, "Crocin", 3, new BigDecimal("25.00"));

        assertEquals(5, carrinho.quantidadeReservada(1L));
        assertEquals(1, carrinho.getItens().size());
    }

    @Test
    void deveCalcularTotalDosItens() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionarItem(1L, "Crocin", 2, new BigDecimal("25.00"));
        carrinho.adicionarItem(2L, "Anacin", 1, new BigDecimal("30.50"));

        assertEquals(0, new BigDecimal("80.50").compareTo(carrinho.getTotal()));
    }

    @Test
    void deveRemoverItemELimparCarrinho() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionarItem(1L, "Crocin", 2, new BigDecimal("25.00"));
        carrinho.adicionarItem(2L, "Anacin", 1, new BigDecimal("30.00"));

        carrinho.removerItem(1L);
        assertEquals(0, carrinho.quantidadeReservada(1L));

        carrinho.limpar();
        assertTrue(carrinho.isVazio());
    }
}
