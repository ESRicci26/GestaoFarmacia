package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.porta.entrada.LojaCasoDeUso;
import br.com.javaricci.aplicacao.porta.saida.LojaRepositorio;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.modelo.Loja;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementa os casos de uso de lojas. */
@Service
public class LojaServico implements LojaCasoDeUso {

    private static final String MENSAGEM_NAO_ENCONTRADA = "Loja não encontrada.";

    private final LojaRepositorio lojaRepositorio;

    public LojaServico(LojaRepositorio lojaRepositorio) {
        this.lojaRepositorio = lojaRepositorio;
    }

    @Override
    @Transactional
    public Long inserir(Loja loja) {
        return lojaRepositorio.inserir(loja);
    }

    @Override
    @Transactional
    public void atualizar(Loja loja) {
        if (!lojaRepositorio.atualizar(loja)) {
            throw new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADA);
        }
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (!lojaRepositorio.excluir(id)) {
            throw new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADA);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loja> listar() {
        return lojaRepositorio.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Loja buscarPorId(Long id) {
        return lojaRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADA));
    }
}
