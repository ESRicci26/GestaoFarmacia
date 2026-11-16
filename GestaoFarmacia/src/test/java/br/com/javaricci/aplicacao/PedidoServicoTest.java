package br.com.javaricci.aplicacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.javaricci.aplicacao.porta.saida.LojaRepositorio;
import br.com.javaricci.aplicacao.porta.saida.MedicamentoRepositorio;
import br.com.javaricci.aplicacao.porta.saida.PedidoRepositorio;
import br.com.javaricci.aplicacao.servico.PedidoServico;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Carrinho;
import br.com.javaricci.dominio.modelo.Loja;
import br.com.javaricci.dominio.modelo.Medicamento;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoServicoTest {

    private static final Long LOJA_ID = 1L;
    private static final Long MEDICAMENTO_ID = 10L;

    private PedidoRepositorio pedidoRepositorio;
    private MedicamentoRepositorio medicamentoRepositorio;
    private LojaRepositorio lojaRepositorio;
    private PedidoServico servico;

    @BeforeEach
    void preparar() {
        pedidoRepositorio = mock(PedidoRepositorio.class);
        medicamentoRepositorio = mock(MedicamentoRepositorio.class);
        lojaRepositorio = mock(LojaRepositorio.class);
        servico = new PedidoServico(pedidoRepositorio, medicamentoRepositorio, lojaRepositorio);

        when(medicamentoRepositorio.buscarPorId(MEDICAMENTO_ID)).thenReturn(Optional.of(criarMedicamento(20)));
        when(lojaRepositorio.buscarPorId(LOJA_ID)).thenReturn(Optional.of(new Loja(LOJA_ID, "Loja 1", "Centro")));
    }

    @Test
    void deveAdicionarItemQuandoDadosSaoValidos() {
        Carrinho carrinho = new Carrinho();

        servico.adicionarItemAoCarrinho(carrinho, MEDICAMENTO_ID, 5, new BigDecimal("30.00"));

        assertEquals(5, carrinho.quantidadeReservada(MEDICAMENTO_ID));
    }

    @Test
    void deveRejeitarPrecoDeVendaMenorQueOPrecoDoFornecedor() {
        Carrinho carrinho = new Carrinho();

        assertThrows(RegraNegocioException.class,
                () -> servico.adicionarItemAoCarrinho(carrinho, MEDICAMENTO_ID, 1, new BigDecimal("19.99")));
    }

    @Test
    void deveRejeitarQuantidadeAcimaDoEstoqueConsiderandoItensJaNoCarrinho() {
        Carrinho carrinho = new Carrinho();
        servico.adicionarItemAoCarrinho(carrinho, MEDICAMENTO_ID, 15, new BigDecimal("25.00"));

        assertThrows(RegraNegocioException.class,
                () -> servico.adicionarItemAoCarrinho(carrinho, MEDICAMENTO_ID, 6, new BigDecimal("25.00")));
    }

    @Test
    void deveRejeitarFinalizacaoComCarrinhoVazio() {
        assertThrows(RegraNegocioException.class, () -> servico.finalizarPedido(LOJA_ID, new Carrinho()));
        verify(pedidoRepositorio, never()).inserir(any());
    }

    @Test
    void deveRejeitarFinalizacaoQuandoEstoqueNaoPodeSerBaixado() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionarItem(MEDICAMENTO_ID, "Crocin", 5, new BigDecimal("25.00"));
        when(pedidoRepositorio.inserir(any())).thenReturn(100L);
        when(medicamentoRepositorio.reduzirEstoque(anyLong(), anyInt())).thenReturn(false);

        assertThrows(RegraNegocioException.class, () -> servico.finalizarPedido(LOJA_ID, carrinho));
        verify(pedidoRepositorio, never()).inserirItem(anyLong(), any());
    }

    @Test
    void deveFinalizarPedidoEBaixarEstoque() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionarItem(MEDICAMENTO_ID, "Crocin", 5, new BigDecimal("25.00"));
        when(pedidoRepositorio.inserir(any())).thenReturn(100L);
        when(medicamentoRepositorio.reduzirEstoque(MEDICAMENTO_ID, 5)).thenReturn(true);

        Long pedidoId = servico.finalizarPedido(LOJA_ID, carrinho);

        assertEquals(100L, pedidoId);
        verify(medicamentoRepositorio).reduzirEstoque(MEDICAMENTO_ID, 5);
        verify(pedidoRepositorio).inserirItem(anyLong(), any());
    }

    private Medicamento criarMedicamento(int estoque) {
        return new Medicamento(MEDICAMENTO_ID, 1L, "Crocin", "Analgésico", "Paracetamol", "Usado para febre",
                estoque, new BigDecimal("20.00"), LocalDate.now().minusYears(1), LocalDate.now().plusYears(1));
    }
}
