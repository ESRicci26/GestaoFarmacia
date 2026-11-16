package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.porta.entrada.PedidoCasoDeUso;
import br.com.javaricci.aplicacao.porta.saida.LojaRepositorio;
import br.com.javaricci.aplicacao.porta.saida.MedicamentoRepositorio;
import br.com.javaricci.aplicacao.porta.saida.PedidoRepositorio;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Carrinho;
import br.com.javaricci.dominio.modelo.ItemCarrinho;
import br.com.javaricci.dominio.modelo.ItemPedido;
import br.com.javaricci.dominio.modelo.Medicamento;
import br.com.javaricci.dominio.modelo.Pedido;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementa os casos de uso de carrinho e pedidos das lojas. */
@Service
public class PedidoServico implements PedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepositorio;
    private final MedicamentoRepositorio medicamentoRepositorio;
    private final LojaRepositorio lojaRepositorio;

    public PedidoServico(PedidoRepositorio pedidoRepositorio,
                         MedicamentoRepositorio medicamentoRepositorio,
                         LojaRepositorio lojaRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.medicamentoRepositorio = medicamentoRepositorio;
        this.lojaRepositorio = lojaRepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public void adicionarItemAoCarrinho(Carrinho carrinho, Long medicamentoId, int quantidade, BigDecimal precoVenda) {
        Medicamento medicamento = buscarMedicamento(medicamentoId);
        validarQuantidadePositiva(quantidade);
        validarPrecoVenda(medicamento, precoVenda);
        validarEstoque(medicamento, quantidade + carrinho.quantidadeReservada(medicamentoId));
        carrinho.adicionarItem(medicamentoId, medicamento.getNome(), quantidade, precoVenda);
    }

    @Override
    @Transactional(readOnly = true)
    public void alterarQuantidadeNoCarrinho(Carrinho carrinho, Long medicamentoId, int novaQuantidade) {
        Medicamento medicamento = buscarMedicamento(medicamentoId);
        validarQuantidadePositiva(novaQuantidade);
        validarEstoque(medicamento, novaQuantidade);
        carrinho.definirQuantidade(medicamentoId, novaQuantidade);
    }

    @Override
    @Transactional
    public Long finalizarPedido(Long lojaId, Carrinho carrinho) {
        if (carrinho.isVazio()) {
            throw new RegraNegocioException("O carrinho está vazio.");
        }
        garantirLojaExistente(lojaId);

        Long pedidoId = pedidoRepositorio.inserir(new Pedido(null, lojaId, null));
        for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
            registrarItemEBaixarEstoque(pedidoId, itemCarrinho);
        }
        return pedidoId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listarPedidosDaLoja(Long lojaId) {
        garantirLojaExistente(lojaId);
        List<Pedido> pedidos = pedidoRepositorio.listarPorLoja(lojaId);
        pedidos.forEach(pedido -> pedido.definirItens(pedidoRepositorio.listarItensPorPedido(pedido.getId())));
        return pedidos;
    }

    @Override
    @Transactional(readOnly = true)
    public Pedido buscarPedido(Long lojaId, Long pedidoId) {
        Pedido pedido = pedidoRepositorio.buscarPorId(pedidoId)
                .filter(encontrado -> encontrado.getLojaId().equals(lojaId))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado."));
        pedido.definirItens(pedidoRepositorio.listarItensPorPedido(pedidoId));
        return pedido;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPedido buscarItemDoPedido(Long pedidoId, Long itemId) {
        return pedidoRepositorio.buscarItemPorId(itemId)
                .filter(item -> item.getPedidoId().equals(pedidoId))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item do pedido não encontrado."));
    }

    private void registrarItemEBaixarEstoque(Long pedidoId, ItemCarrinho itemCarrinho) {
        boolean estoqueBaixado = medicamentoRepositorio.reduzirEstoque(
                itemCarrinho.getMedicamentoId(), itemCarrinho.getQuantidade());
        if (!estoqueBaixado) {
            throw new RegraNegocioException("Estoque insuficiente para o medicamento "
                    + itemCarrinho.getNomeMedicamento() + ". Revise o carrinho.");
        }
        pedidoRepositorio.inserirItem(pedidoId, new ItemPedido(
                null, pedidoId, itemCarrinho.getMedicamentoId(), itemCarrinho.getNomeMedicamento(),
                itemCarrinho.getQuantidade(), itemCarrinho.getPrecoVenda()));
    }

    private Medicamento buscarMedicamento(Long medicamentoId) {
        return medicamentoRepositorio.buscarPorId(medicamentoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medicamento não encontrado."));
    }

    private void garantirLojaExistente(Long lojaId) {
        if (lojaRepositorio.buscarPorId(lojaId).isEmpty()) {
            throw new RecursoNaoEncontradoException("Loja não encontrada.");
        }
    }

    private void validarQuantidadePositiva(int quantidade) {
        if (quantidade <= 0) {
            throw new RegraNegocioException("Selecione ao menos 1 unidade.");
        }
    }

    private void validarPrecoVenda(Medicamento medicamento, BigDecimal precoVenda) {
        if (precoVenda == null || precoVenda.compareTo(medicamento.getPreco()) < 0) {
            throw new RegraNegocioException("O preço de venda não pode ser menor que o preço do fornecedor.");
        }
    }

    private void validarEstoque(Medicamento medicamento, int quantidadeDesejada) {
        if (quantidadeDesejada > medicamento.getQuantidadeDisponivel()) {
            throw new RegraNegocioException("Quantidade solicitada maior que o estoque disponível ("
                    + medicamento.getQuantidadeDisponivel() + ").");
        }
    }
}
