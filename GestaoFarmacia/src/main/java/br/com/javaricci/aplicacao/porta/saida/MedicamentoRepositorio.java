package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Medicamento;
import java.util.List;
import java.util.Optional;

/** Porta de saída para persistência de medicamentos. */
public interface MedicamentoRepositorio {

    Long inserir(Medicamento medicamento);

    boolean atualizar(Medicamento medicamento);

    boolean excluir(Long id);

    List<Medicamento> listar();

    List<Medicamento> listarPorFarmacia(Long farmaciaId);

    List<Medicamento> listarPorNome(String termo);

    Optional<Medicamento> buscarPorId(Long id);

    /** Baixa atômica de estoque. Retorna false quando não há quantidade suficiente. */
    boolean reduzirEstoque(Long medicamentoId, int quantidade);
}
