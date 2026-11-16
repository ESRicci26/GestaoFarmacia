package br.com.javaricci.dominio.modelo;

/** Farmácia fornecedora responsável por um catálogo de medicamentos. */
public class Farmacia {

    private final Long id;
    private final String nome;
    private final String localizacao;

    public Farmacia(Long id, String nome, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }
}
