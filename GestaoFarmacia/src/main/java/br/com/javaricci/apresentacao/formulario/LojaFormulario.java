package br.com.javaricci.apresentacao.formulario;

import br.com.javaricci.dominio.modelo.Loja;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados do formulário de loja. */
public class LojaFormulario {

    @NotBlank(message = "O nome da loja é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "A localização é obrigatória.")
    @Size(max = 150, message = "A localização deve ter no máximo 150 caracteres.")
    private String localizacao;

    public static LojaFormulario deDominio(Loja loja) {
        LojaFormulario formulario = new LojaFormulario();
        formulario.nome = loja.getNome();
        formulario.localizacao = loja.getLocalizacao();
        return formulario;
    }

    public Loja paraDominio(Long id) {
        return new Loja(id, nome.trim(), localizacao.trim());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
