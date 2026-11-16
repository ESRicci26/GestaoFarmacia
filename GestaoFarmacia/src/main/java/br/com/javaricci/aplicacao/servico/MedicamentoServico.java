package br.com.javaricci.aplicacao.servico;

import br.com.javaricci.aplicacao.porta.entrada.MedicamentoCasoDeUso;
import br.com.javaricci.aplicacao.porta.saida.FarmaciaRepositorio;
import br.com.javaricci.aplicacao.porta.saida.MedicamentoRepositorio;
import br.com.javaricci.aplicacao.validacao.ValidadorMedicamento;
import br.com.javaricci.dominio.excecao.RecursoNaoEncontradoException;
import br.com.javaricci.dominio.modelo.Medicamento;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementa os casos de uso de medicamentos. */
@Service
public class MedicamentoServico implements MedicamentoCasoDeUso {

    private static final String MENSAGEM_NAO_ENCONTRADO = "Medicamento não encontrado.";

    private final MedicamentoRepositorio medicamentoRepositorio;
    private final FarmaciaRepositorio farmaciaRepositorio;
    private final ValidadorMedicamento validador;

    public MedicamentoServico(MedicamentoRepositorio medicamentoRepositorio,
                              FarmaciaRepositorio farmaciaRepositorio,
                              ValidadorMedicamento validador) {
        this.medicamentoRepositorio = medicamentoRepositorio;
        this.farmaciaRepositorio = farmaciaRepositorio;
        this.validador = validador;
    }

    @Override
    @Transactional
    public Long inserir(Medicamento medicamento) {
        validador.validar(medicamento);
        garantirFarmaciaExistente(medicamento.getFarmaciaId());
        return medicamentoRepositorio.inserir(medicamento);
    }

    @Override
    @Transactional
    public void atualizar(Medicamento medicamento) {
        validador.validar(medicamento);
        if (!medicamentoRepositorio.atualizar(medicamento)) {
            throw new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADO);
        }
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (!medicamentoRepositorio.excluir(id)) {
            throw new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADO);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> listarPorFarmacia(Long farmaciaId) {
        garantirFarmaciaExistente(farmaciaId);
        return medicamentoRepositorio.listarPorFarmacia(farmaciaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> pesquisar(String termo) {
        String termoLimpo = termo == null ? "" : termo.trim();
        if (termoLimpo.isEmpty()) {
            return medicamentoRepositorio.listar();
        }
        if (termoLimpo.chars().allMatch(Character::isDigit) && termoLimpo.length() <= 18) {
            Optional<Medicamento> porId = medicamentoRepositorio.buscarPorId(Long.parseLong(termoLimpo));
            return porId.map(List::of).orElseGet(List::of);
        }
        return medicamentoRepositorio.listarPorNome(termoLimpo);
    }

    @Override
    @Transactional(readOnly = true)
    public Medicamento buscarPorId(Long id) {
        return medicamentoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_NAO_ENCONTRADO));
    }

    private void garantirFarmaciaExistente(Long farmaciaId) {
        if (farmaciaRepositorio.buscarPorId(farmaciaId).isEmpty()) {
            throw new RecursoNaoEncontradoException("Farmácia não encontrada.");
        }
    }
}
