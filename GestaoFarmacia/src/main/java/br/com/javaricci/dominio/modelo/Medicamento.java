package br.com.javaricci.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Medicamento do catálogo de uma farmácia. O preço é o valor cobrado pelo fornecedor. */
public class Medicamento {

    private final Long id;
    private final Long farmaciaId;
    private final String nome;
    private final String tipo;
    private final String composicao;
    private final String descricao;
    private final int quantidadeDisponivel;
    private final BigDecimal preco;
    private final LocalDate dataFabricacao;
    private final LocalDate dataValidade;

    public Medicamento(Long id, Long farmaciaId, String nome, String tipo, String composicao,
                       String descricao, int quantidadeDisponivel, BigDecimal preco,
                       LocalDate dataFabricacao, LocalDate dataValidade) {
        this.id = id;
        this.farmaciaId = farmaciaId;
        this.nome = nome;
        this.tipo = tipo;
        this.composicao = composicao;
        this.descricao = descricao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.preco = preco;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
    }

    /** Verifica se a validade do medicamento já passou. */
    public boolean estaVencido() {
        return dataValidade.isBefore(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public Long getFarmaciaId() {
        return farmaciaId;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getComposicao() {
        return composicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }
}
