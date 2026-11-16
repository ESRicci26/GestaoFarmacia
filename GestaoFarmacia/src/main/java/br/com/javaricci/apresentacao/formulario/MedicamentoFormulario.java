package br.com.javaricci.apresentacao.formulario;

import br.com.javaricci.dominio.modelo.Medicamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/** Dados do formulário de medicamento. */
public class MedicamentoFormulario {

    @NotBlank(message = "O nome do medicamento é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O tipo é obrigatório.")
    @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres.")
    private String tipo;

    @NotBlank(message = "A composição é obrigatória.")
    @Size(max = 150, message = "A composição deve ter no máximo 150 caracteres.")
    private String composicao;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    @NotNull(message = "Informe a quantidade disponível.")
    @Min(value = 0, message = "A quantidade não pode ser negativa.")
    private Integer quantidadeDisponivel;

    @NotNull(message = "Informe o preço.")
    @DecimalMin(value = "0.00", message = "O preço não pode ser negativo.")
    private BigDecimal preco;

    @NotNull(message = "Informe a data de fabricação.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataFabricacao;

    @NotNull(message = "Informe a data de validade.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataValidade;

    public static MedicamentoFormulario deDominio(Medicamento medicamento) {
        MedicamentoFormulario formulario = new MedicamentoFormulario();
        formulario.nome = medicamento.getNome();
        formulario.tipo = medicamento.getTipo();
        formulario.composicao = medicamento.getComposicao();
        formulario.descricao = medicamento.getDescricao();
        formulario.quantidadeDisponivel = medicamento.getQuantidadeDisponivel();
        formulario.preco = medicamento.getPreco();
        formulario.dataFabricacao = medicamento.getDataFabricacao();
        formulario.dataValidade = medicamento.getDataValidade();
        return formulario;
    }

    public Medicamento paraDominio(Long id, Long farmaciaId) {
        return new Medicamento(id, farmaciaId, nome.trim(), tipo.trim(), composicao.trim(),
                descricao.trim(), quantidadeDisponivel, preco, dataFabricacao, dataValidade);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }
}
