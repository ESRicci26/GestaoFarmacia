package br.com.javaricci.apresentacao.web;

import br.com.javaricci.aplicacao.porta.entrada.FarmaciaCasoDeUso;
import br.com.javaricci.aplicacao.porta.entrada.LojaCasoDeUso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Páginas de entrada: início, painel administrativo e seleção de perfil de acesso. */
@Controller
public class InicioControlador {

    private final FarmaciaCasoDeUso farmaciaCasoDeUso;
    private final LojaCasoDeUso lojaCasoDeUso;

    public InicioControlador(FarmaciaCasoDeUso farmaciaCasoDeUso, LojaCasoDeUso lojaCasoDeUso) {
        this.farmaciaCasoDeUso = farmaciaCasoDeUso;
        this.lojaCasoDeUso = lojaCasoDeUso;
    }

    @GetMapping("/")
    public String exibirInicio() {
        return "inicio";
    }

    @GetMapping("/admin")
    public String exibirPainelAdministrativo() {
        return "admin/painel";
    }

    @GetMapping("/acesso/farmacia")
    public String exibirAcessoFarmacia(Model model) {
        model.addAttribute("farmacias", farmaciaCasoDeUso.listar());
        return "acesso/farmacia";
    }

    @GetMapping("/acesso/loja")
    public String exibirAcessoLoja(Model model) {
        model.addAttribute("lojas", lojaCasoDeUso.listar());
        return "acesso/loja";
    }
}
