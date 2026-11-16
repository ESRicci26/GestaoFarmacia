package br.com.javaricci.apresentacao.web;

import br.com.javaricci.aplicacao.porta.entrada.FarmaciaCasoDeUso;
import br.com.javaricci.apresentacao.formulario.FarmaciaFormulario;
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

/** Gerenciamento (CRUD) de farmácias. */
@Controller
@RequestMapping("/farmacias")
public class FarmaciaControlador {

    private static final String VISAO_FORMULARIO = "farmacias/formulario";
    private static final String REDIRECIONA_LISTA = "redirect:/farmacias";

    private final FarmaciaCasoDeUso farmaciaCasoDeUso;

    public FarmaciaControlador(FarmaciaCasoDeUso farmaciaCasoDeUso) {
        this.farmaciaCasoDeUso = farmaciaCasoDeUso;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("farmacias", farmaciaCasoDeUso.listar());
        return "farmacias/lista";
    }

    @GetMapping("/nova")
    public String exibirFormularioDeInsercao(Model model) {
        model.addAttribute("formulario", new FarmaciaFormulario());
        prepararFormulario(model, "Nova farmácia", "/farmacias");
        return VISAO_FORMULARIO;
    }

    @PostMapping
    public String inserir(@Valid @ModelAttribute("formulario") FarmaciaFormulario formulario,
                          BindingResult resultado, Model model, RedirectAttributes redirecionamento) {
        if (resultado.hasErrors()) {
            prepararFormulario(model, "Nova farmácia", "/farmacias");
            return VISAO_FORMULARIO;
        }
        farmaciaCasoDeUso.inserir(formulario.paraDominio(null));
        redirecionamento.addFlashAttribute("mensagemSucesso", "Farmácia cadastrada com sucesso.");
        return REDIRECIONA_LISTA;
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioDeEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("formulario", FarmaciaFormulario.deDominio(farmaciaCasoDeUso.buscarPorId(id)));
        prepararFormulario(model, "Editar farmácia", "/farmacias/" + id + "/atualizar");
        return VISAO_FORMULARIO;
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("formulario") FarmaciaFormulario formulario,
                            BindingResult resultado, Model model, RedirectAttributes redirecionamento) {
        if (resultado.hasErrors()) {
            prepararFormulario(model, "Editar farmácia", "/farmacias/" + id + "/atualizar");
            return VISAO_FORMULARIO;
        }
        farmaciaCasoDeUso.atualizar(formulario.paraDominio(id));
        redirecionamento.addFlashAttribute("mensagemSucesso", "Farmácia atualizada com sucesso.");
        return REDIRECIONA_LISTA;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirecionamento) {
        try {
            farmaciaCasoDeUso.excluir(id);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Farmácia excluída com sucesso.");
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
