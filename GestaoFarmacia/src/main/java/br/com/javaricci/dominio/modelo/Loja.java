package br.com.javaricci.dominio.modelo;

/** Loja que consulta o catálogo das farmácias e realiza pedidos. */
public class Loja {

    private final Long id;
    private final String nome;
    private final String localizacao;

    public Loja(Long id, String nome, String localizacao) {
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
