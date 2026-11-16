package br.com.javaricci.dominio.excecao;

/** Indica que o registro solicitado não existe. */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
