package br.com.javaricci.apresentacao.web;

import br.com.javaricci.aplicacao.porta.entrada.FarmaciaCasoDeUso;
import br.com.javaricci.aplicacao.porta.entrada.LojaCasoDeUso;
import br.com.javaricci.aplicacao.porta.entrada.MedicamentoCasoDeUso;
import br.com.javaricci.aplicacao.porta.entrada.PedidoCasoDeUso;
import br.com.javaricci.apresentacao.sessao.CarrinhoSessao;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Carrinho;
import br.com.javaricci.dominio.modelo.Farmacia;
import br.com.javaricci.dominio.modelo.Medicamento;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Área da loja: painel, navegação pelo catálogo e operações do carrinho. */
@Controller
@RequestMapping("/lojas/{lojaId}")
public class LojaOperacaoControlador {

    private final LojaCasoDeUso lojaCasoDeUso;
    private final FarmaciaCasoDeUso farmaciaCasoDeUso;
    private final MedicamentoCasoDeUso medicamentoCasoDeUso;
    private final PedidoCasoDeUso pedidoCasoDeUso;
    private final CarrinhoSessao carrinhoSessao;

    public LojaOperacaoControlador(LojaCasoDeUso lojaCasoDeUso,
                                   FarmaciaCasoDeUso farmaciaCasoDeUso,
                                   MedicamentoCasoDeUso medicamentoCasoDeUso,
                                   PedidoCasoDeUso pedidoCasoDeUso,
                                   CarrinhoSessao carrinhoSessao) {
        this.lojaCasoDeUso = lojaCasoDeUso;
        this.farmaciaCasoDeUso = farmaciaCasoDeUso;
        this.medicamentoCasoDeUso = medicamentoCasoDeUso;
        this.pedidoCasoDeUso = pedidoCasoDeUso;
        this.carrinhoSessao = carrinhoSessao;
    }

    @GetMapping("/painel")
    public String exibirPainel(@PathVariable Long lojaId, Model model) {
        model.addAttribute("loja", lojaCasoDeUso.buscarPorId(lojaId));
        return "loja/painel";
    }

    @GetMapping("/catalogo")
    public String exibirCatalogo(@PathVariable Long lojaId,
                                 @RequestParam(required = false) Long farmaciaId,
                                 @RequestParam(required = false) String busca,
                                 Model model) {
        List<Farmacia> farmacias = farmaciaCasoDeUso.listar();
        Long farmaciaSelecionada = definirFarmaciaSelecionada(farmaciaId, farmacias);

        model.addAttribute("loja", lojaCasoDeUso.buscarPorId(lojaId));
        model.addAttribute("farmacias", farmacias);
        model.addAttribute("farmaciaSelecionadaId", farmaciaSelecionada);
        model.addAttribute("busca", busca);
        model.addAttribute("medicamentos", consultarMedicamentos(farmaciaSelecionada, busca));
        model.addAttribute("carrinho", carrinhoSessao.obterCarrinho(lojaId));
        return "loja/catalogo";
    }

    @GetMapping("/catalogo/{medicamentoId}")
    public String exibirDetalheDoMedicamento(@PathVariable Long lojaId,
                                             @PathVariable Long medicamentoId, Model model) {
        Medicamento medicamento = medicamentoCasoDeUso.buscarPorId(medicamentoId);
        model.addAttribute("loja", lojaCasoDeUso.buscarPorId(lojaId));
        model.addAttribute("medicamento", medicamento);
        model.addAttribute("farmacia", farmaciaCasoDeUso.buscarPorId(medicamento.getFarmaciaId()));
        return "loja/detalhe-medicamento";
    }

    @PostMapping("/carrinho/itens")
    public String adicionarItem(@PathVariable Long lojaId,
                                @RequestParam Long medicamentoId,
                                @RequestParam int quantidade,
                                @RequestParam(required = false) BigDecimal precoVenda,
                                @RequestParam(required = false) Long farmaciaId,
                                @RequestParam(required = false) String busca,
                                RedirectAttributes redirecionamento) {
        Carrinho carrinho = carrinhoSessao.obterCarrinho(lojaId);
        executarComMensagem(redirecionamento, "Item adicionado ao carrinho.",
                () -> pedidoCasoDeUso.adicionarItemAoCarrinho(carrinho, medicamentoId, quantidade, precoVenda));
        return redirecionarParaCatalogo(lojaId, farmaciaId, busca, redirecionamento);
    }

    @PostMapping("/carrinho/itens/{medicamentoId}/quantidade")
    public String alterarQuantidade(@PathVariable Long lojaId,
                                    @PathVariable Long medicamentoId,
                                    @RequestParam int novaQuantidade,
                                    @RequestParam(required = false) Long farmaciaId,
                                    @RequestParam(required = false) String busca,
                                    RedirectAttributes redirecionamento) {
        Carrinho carrinho = carrinhoSessao.obterCarrinho(lojaId);
        executarComMensagem(redirecionamento, "Quantidade atualizada.",
                () -> pedidoCasoDeUso.alterarQuantidadeNoCarrinho(carrinho, medicamentoId, novaQuantidade));
        return redirecionarParaCatalogo(lojaId, farmaciaId, busca, redirecionamento);
    }

    @PostMapping("/carrinho/itens/{medicamentoId}/remover")
    public String removerItem(@PathVariable Long lojaId,
                              @PathVariable Long medicamentoId,
                              @RequestParam(required = false) Long farmaciaId,
                              @RequestParam(required = false) String busca,
                              RedirectAttributes redirecionamento) {
        carrinhoSessao.obterCarrinho(lojaId).removerItem(medicamentoId);
        redirecionamento.addFlashAttribute("mensagemSucesso", "Item removido do carrinho.");
        return redirecionarParaCatalogo(lojaId, farmaciaId, busca, redirecionamento);
    }

    @PostMapping("/carrinho/finalizar")
    public String finalizarPedido(@PathVariable Long lojaId, RedirectAttributes redirecionamento) {
        try {
            Long pedidoId = pedidoCasoDeUso.finalizarPedido(lojaId, carrinhoSessao.obterCarrinho(lojaId));
            carrinhoSessao.descartarCarrinho(lojaId);
            redirecionamento.addFlashAttribute("mensagemSucesso",
                    "Pedido #" + pedidoId + " finalizado com sucesso.");
            return "redirect:/lojas/" + lojaId + "/pedidos/" + pedidoId;
        } catch (RegraNegocioException excecao) {
            redirecionamento.addFlashAttribute("mensagemErro", excecao.getMessage());
            return "redirect:/lojas/" + lojaId + "/catalogo";
        }
    }

    private Long definirFarmaciaSelecionada(Long farmaciaId, List<Farmacia> farmacias) {
        if (farmaciaId != null) {
            return farmaciaId;
        }
        return farmacias.isEmpty() ? null : farmacias.get(0).getId();
    }

    private List<Medicamento> consultarMedicamentos(Long farmaciaId, String busca) {
        if (busca != null && !busca.isBlank()) {
            return medicamentoCasoDeUso.pesquisar(busca);
        }
        return farmaciaId == null ? List.of() : medicamentoCasoDeUso.listarPorFarmacia(farmaciaId);
    }

    private void executarComMensagem(RedirectAttributes redirecionamento, String mensagemSucesso,
                                     Runnable operacao) {
        try {
            operacao.run();
            redirecionamento.addFlashAttribute("mensagemSucesso", mensagemSucesso);
        } catch (RegraNegocioException excecao) {
            redirecionamento.addFlashAttribute("mensagemErro", excecao.getMessage());
        }
    }

    private String redirecionarParaCatalogo(Long lojaId, Long farmaciaId, String busca,
                                            RedirectAttributes redirecionamento) {
        if (farmaciaId != null) {
            redirecionamento.addAttribute("farmaciaId", farmaciaId);
        }
        if (busca != null && !busca.isBlank()) {
            redirecionamento.addAttribute("busca", busca);
        }
        return "redirect:/lojas/" + lojaId + "/catalogo";
    }
}
