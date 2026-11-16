package br.com.javaricci.apresentacao.web;

import br.com.javaricci.aplicacao.porta.entrada.FarmaciaCasoDeUso;
import br.com.javaricci.aplicacao.porta.entrada.MedicamentoCasoDeUso;
import br.com.javaricci.apresentacao.formulario.MedicamentoFormulario;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Farmacia;
import br.com.javaricci.dominio.modelo.Medicamento;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Gerenciamento (CRUD) do catálogo de medicamentos de cada farmácia. */
@Controller
public class MedicamentoControlador {

    private static final String VISAO_FORMULARIO = "medicamentos/formulario";

    private final MedicamentoCasoDeUso medicamentoCasoDeUso;
    private final FarmaciaCasoDeUso farmaciaCasoDeUso;

    public MedicamentoControlador(MedicamentoCasoDeUso medicamentoCasoDeUso,
                                  FarmaciaCasoDeUso farmaciaCasoDeUso) {
        this.medicamentoCasoDeUso = medicamentoCasoDeUso;
        this.farmaciaCasoDeUso = farmaciaCasoDeUso;
    }

    @GetMapping("/farmacias/{farmaciaId}/medicamentos")
    public String listar(@PathVariable Long farmaciaId, Model model) {
        model.addAttribute("farmacia", farmaciaCasoDeUso.buscarPorId(farmaciaId));
        model.addAttribute("medicamentos", medicamentoCasoDeUso.listarPorFarmacia(farmaciaId));
        return "medicamentos/lista";
    }

    @GetMapping("/farmacias/{farmaciaId}/medicamentos/novo")
    public String exibirFormularioDeInsercao(@PathVariable Long farmaciaId, Model model) {
        model.addAttribute("formulario", new MedicamentoFormulario());
        prepararFormulario(model, farmaciaCasoDeUso.buscarPorId(farmaciaId), "Novo medicamento",
                "/farmacias/" + farmaciaId + "/medicamentos");
        return VISAO_FORMULARIO;
    }

    @PostMapping("/farmacias/{farmaciaId}/medicamentos")
    public String inserir(@PathVariable Long farmaciaId,
                          @Valid @ModelAttribute("formulario") MedicamentoFormulario formulario,
                          BindingResult resultado, Model model, RedirectAttributes redirecionamento) {
        Farmacia farmacia = farmaciaCasoDeUso.buscarPorId(farmaciaId);
        String acao = "/farmacias/" + farmaciaId + "/medicamentos";
        if (resultado.hasErrors()) {
            prepararFormulario(model, farmacia, "Novo medicamento", acao);
            return VISAO_FORMULARIO;
        }
        try {
            medicamentoCasoDeUso.inserir(formulario.paraDominio(null, farmaciaId));
        } catch (RegraNegocioException excecao) {
            resultado.reject("regraNegocio", excecao.getMessage());
            prepararFormulario(model, farmacia, "Novo medicamento", acao);
            return VISAO_FORMULARIO;
        }
        redirecionamento.addFlashAttribute("mensagemSucesso", "Medicamento cadastrado com sucesso.");
        return redirecionarParaLista(farmaciaId);
    }

    @GetMapping("/medicamentos/{id}/editar")
    public String exibirFormularioDeEdicao(@PathVariable Long id, Model model) {
        Medicamento medicamento = medicamentoCasoDeUso.buscarPorId(id);
        model.addAttribute("formulario", MedicamentoFormulario.deDominio(medicamento));
        prepararFormulario(model, farmaciaCasoDeUso.buscarPorId(medicamento.getFarmaciaId()),
                "Editar medicamento", "/medicamentos/" + id + "/atualizar");
        return VISAO_FORMULARIO;
    }

    @PostMapping("/medicamentos/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("formulario") MedicamentoFormulario formulario,
                            BindingResult resultado, Model model, RedirectAttributes redirecionamento) {
        Medicamento existente = medicamentoCasoDeUso.buscarPorId(id);
        Farmacia farmacia = farmaciaCasoDeUso.buscarPorId(existente.getFarmaciaId());
        String acao = "/medicamentos/" + id + "/atualizar";
        if (resultado.hasErrors()) {
            prepararFormulario(model, farmacia, "Editar medicamento", acao);
            return VISAO_FORMULARIO;
        }
        try {
            medicamentoCasoDeUso.atualizar(formulario.paraDominio(id, existente.getFarmaciaId()));
        } catch (RegraNegocioException excecao) {
            resultado.reject("regraNegocio", excecao.getMessage());
            prepararFormulario(model, farmacia, "Editar medicamento", acao);
            return VISAO_FORMULARIO;
        }
        redirecionamento.addFlashAttribute("mensagemSucesso", "Medicamento atualizado com sucesso.");
        return redirecionarParaLista(existente.getFarmaciaId());
    }

    @PostMapping("/medicamentos/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirecionamento) {
        Medicamento existente = medicamentoCasoDeUso.buscarPorId(id);
        try {
            medicamentoCasoDeUso.excluir(id);
            redirecionamento.addFlashAttribute("mensagemSucesso", "Medicamento excluído com sucesso.");
        } catch (RegraNegocioException excecao) {
            redirecionamento.addFlashAttribute("mensagemErro", excecao.getMessage());
        }
        return redirecionarParaLista(existente.getFarmaciaId());
    }

    private void prepararFormulario(Model model, Farmacia farmacia, String titulo, String acao) {
        model.addAttribute("farmacia", farmacia);
        model.addAttribute("titulo", titulo);
        model.addAttribute("acao", acao);
    }

    private String redirecionarParaLista(Long farmaciaId) {
        return "redirect:/farmacias/" + farmaciaId + "/medicamentos";
    }
}
