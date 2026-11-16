package br.com.javaricci.apresentacao.formulario;

import br.com.javaricci.dominio.modelo.Farmacia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados do formulário de farmácia. */
public class FarmaciaFormulario {

    @NotBlank(message = "O nome da farmácia é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @Size(max = 150, message = "A localização deve ter no máximo 150 caracteres.")
    private String localizacao;

    public static FarmaciaFormulario deDominio(Farmacia farmacia) {
        FarmaciaFormulario formulario = new FarmaciaFormulario();
        formulario.nome = farmacia.getNome();
        formulario.localizacao = farmacia.getLocalizacao();
        return formulario;
    }

    public Farmacia paraDominio(Long id) {
        return new Farmacia(id, nome.trim(), localizacao == null ? null : localizacao.trim());
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
