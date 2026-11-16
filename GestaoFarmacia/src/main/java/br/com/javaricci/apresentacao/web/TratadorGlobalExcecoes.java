package br.com.javaricci.apresentacao.web;

import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** Converte exceções em páginas de erro amigáveis. */
@ControllerAdvice
public class TratadorGlobalExcecoes {

    private static final Logger LOG = LoggerFactory.getLogger(TratadorGlobalExcecoes.class);
    private static final String VISAO_ERRO = "erro";

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarNaoEncontrado(RecursoNaoEncontradoException excecao, Model model) {
        return montarErro(model, "Não encontrado", excecao.getMessage());
    }

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String tratarRegraDeNegocio(RegraNegocioException excecao, Model model) {
        return montarErro(model, "Operação não permitida", excecao.getMessage());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tratarRequisicaoInvalida(Exception excecao, Model model) {
        return montarErro(model, "Requisição inválida", "Os dados enviados são inválidos. Revise o formulário.");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String tratarErroInesperado(RuntimeException excecao, Model model) {
        LOG.error("Erro inesperado na aplicação", excecao);
        return montarErro(model, "Erro interno", "Ocorreu um erro inesperado. Tente novamente em instantes.");
    }

    private String montarErro(Model model, String titulo, String mensagem) {
        model.addAttribute("tituloErro", titulo);
        model.addAttribute("detalheErro", mensagem);
        return VISAO_ERRO;
    }
}
