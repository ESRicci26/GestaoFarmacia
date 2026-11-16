package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.dominio.modelo.Medicamento;
import java.util.List;

/** Casos de uso de gerenciamento e consulta de medicamentos. */
public interface MedicamentoCasoDeUso {

    Long inserir(Medicamento medicamento);

    void atualizar(Medicamento medicamento);

    void excluir(Long id);

    List<Medicamento> listarPorFarmacia(Long farmaciaId);

    /** Pesquisa por código numérico (ID) ou por parte do nome. */
    List<Medicamento> pesquisar(String termo);

    Medicamento buscarPorId(Long id);
}
