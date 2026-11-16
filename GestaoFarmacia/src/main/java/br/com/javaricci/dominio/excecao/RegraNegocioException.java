package br.com.javaricci.dominio.excecao;

/** Violação de uma regra de negócio (dados inválidos, estoque insuficiente, vínculo existente etc.). */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
