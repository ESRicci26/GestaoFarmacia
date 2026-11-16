package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.porta.entrada.FarmaciaCasoDeUso;
import br.com.javaricci.aplicacao.porta.saida.FarmaciaRepositorio;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.modelo.Farmacia;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementa os casos de uso de farmácias. */
@Service
public class FarmaciaServico implements FarmaciaCasoDeUso {

    private static final String MENSAGEM_NAO_ENCONTRADA = "Farmácia não encontrada.";

    private final FarmaciaRepositorio farmaciaRepositorio;

    public FarmaciaServico(FarmaciaRepositorio farmaciaRepositorio) {
        this.farmaciaRepositorio = farmaciaRepositorio;
    }

    @Override
    @Transactional
    public Long inserir(Farmacia farmacia) {
        return farmaciaRepositorio.inserir(farmacia);
    }

    @Override
    @Transactional
    public void atualizar(Farmacia farmacia) {
        if (!farmaciaRepositorio.atualizar(farmacia)) {
            throw new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADA);
        }
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (!farmaciaRepositorio.excluir(id)) {
            throw new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADA);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> listar() {
        return farmaciaRepositorio.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Farmacia buscarPorId(Long id) {
        return farmaciaRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADA));
    }
}
