package br.com.javaricci.apresentacao.web;

import br.com.javaricci.aplicacao.porta.entrada.LojaCasoDeUso;
import br.com.javaricci.aplicacao.porta.entrada.PedidoCasoDeUso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** Consulta dos pedidos já finalizados por uma loja. */
@Controller
@RequestMapping("/lojas/{lojaId}/pedidos")
public class PedidoControlador {

    private final LojaCasoDeUso lojaCasoDeUso;
    private final PedidoCasoDeUso pedidoCasoDeUso;

    public PedidoControlador(LojaCasoDeUso lojaCasoDeUso, PedidoCasoDeUso pedidoCasoDeUso) {
        this.lojaCasoDeUso = lojaCasoDeUso;
        this.pedidoCasoDeUso = pedidoCasoDeUso;
    }

    @GetMapping
    public String listar(@PathVariable Long lojaId, Model model) {
        model.addAttribute("loja", lojaCasoDeUso.buscarPorId(lojaId));
        model.addAttribute("pedidos", pedidoCasoDeUso.listarPedidosDaLoja(lojaId));
        return "pedidos/lista";
    }

    @GetMapping("/{pedidoId}")
    public String exibirDetalhe(@PathVariable Long lojaId, @PathVariable Long pedidoId, Model model) {
        model.addAttribute("loja", lojaCasoDeUso.buscarPorId(lojaId));
        model.addAttribute("pedido", pedidoCasoDeUso.buscarPedido(lojaId, pedidoId));
        return "pedidos/detalhe";
    }

    @GetMapping("/{pedidoId}/itens/{itemId}")
    public String exibirItem(@PathVariable Long lojaId, @PathVariable Long pedidoId,
                             @PathVariable Long itemId, Model model) {
        model.addAttribute("loja", lojaCasoDeUso.buscarPorId(lojaId));
        model.addAttribute("pedido", pedidoCasoDeUso.buscarPedido(lojaId, pedidoId));
        model.addAttribute("item", pedidoCasoDeUso.buscarItemDoPedido(pedidoId, itemId));
        return "pedidos/item";
    }
}
