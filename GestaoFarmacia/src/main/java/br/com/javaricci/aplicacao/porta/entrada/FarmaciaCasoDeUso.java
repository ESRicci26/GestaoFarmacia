package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.dominio.modelo.Farmacia;
import java.util.List;

/** Casos de uso de gerenciamento de farmácias. */
public interface FarmaciaCasoDeUso {

    Long inserir(Farmacia farmacia);

    void atualizar(Farmacia farmacia);

    void excluir(Long id);

    List<Farmacia> listar();

    Farmacia buscarPorId(Long id);
}
