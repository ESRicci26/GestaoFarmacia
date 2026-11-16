package br.com.javaricci.apresentacao.web;

import br.com.javaricci.aplicacao.porta.entrada.LojaCasoDeUso;
import br.com.javaricci.apresentacao.formulario.LojaFormulario;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Gerenciamento (CRUD) de lojas. */
@Controller
@RequestMapping("/lojas")
public class LojaControlador {

    private static final String VISAO_FORMULARIO = "lojas/formulario";
    private static final String REDIRECIONA_LISTA = "redirect:/lojas";

    private final LojaCasoDeUso lojaCasoDeUso;

    public LojaControlador(LojaCasoDeUso lojaCasoDeUso) {
        this.lojaCasoDeUso = lojaCasoDeUso;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lojas", lojaCasoDeUso.listar());
        return "lojas/lista";
    }

    @GetMapping("/nova")
    public String exibirFormularioDeInsercao(Model model) {
        model.addAttribute("formulario", new LojaFormulario());
        prepararFormulario(model, "Nova loja", "/lojas");
        return VISAO_FORMULARIO;
    }

    @PostMapping
    public String inserir(@Valid @ModelAttribute("formulario") LojaFormulario formulario,
                          BindingResult resultado, Model model, RedirectAttributes redirecionamento) {
        if (resultado.hasErrors()) {
            prepararFormulario(model, "Nova loja", "/lojas");
            return VISAO_FORMULARIO;
        }
        lojaCasoDeUso.inserir(formulario.paraDominio(null));
        redirecionamento.addFlashAttribute("mensagemSucesso", "Loja cadastrada com sucesso.");
        return REDIRECIONA_LISTA;
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioDeEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("formulario", LojaFormulario.deDominio(lojaCasoDeUso.buscarPorId(id)));
        prepararFormulario(model, "Editar loja", "/lojas/" + id + "/atualizar");
        return VISAO_FORMULARIO;
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("formulario") LojaFormulario formulario,
                            BindingResult resultado, Model model, RedirectAttributes redirecionamento) {
        if (resultado.hasErrors()) {
            prepararFormulario(model, "Editar loja", "/lojas/" + id + "/atualizar");
            return VISAO_FORMULARIO;
        }
        lojaCasoDeUso.atualizar(formulario.paraDominio(id));
        redirecionamento.addFlashAttribute("mensagemSucesso", "Loja atualizada com sucesso.");
        return REDIRECIONA_LISTA;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirecionamento) {
        try {
            lojaCasoDeUso.excluir(id);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Loja excluída com sucesso.");
        } catch (RegraNegocioException excecao) {
            redirecionamento.addFlashAttribute("mensagemErro", excecao.getMessage());
        }
        return REDIRECIONA_LISTA;
    }

    private void prepararFormulario(Model model, String titulo, String acao) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("acao", acao);
    }
}
