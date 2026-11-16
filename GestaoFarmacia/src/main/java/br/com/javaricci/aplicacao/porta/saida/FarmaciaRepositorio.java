package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Farmacia;
import java.util.List;
import java.util.Optional;

/** Porta de saída para persistência de farmácias. */
public interface FarmaciaRepositorio {

    Long inserir(Farmacia farmacia);

    boolean atualizar(Farmacia farmacia);

    boolean excluir(Long id);

    List<Farmacia> listar();

    Optional<Farmacia> buscarPorId(Long id);
}
