package br.com.javaricci.aplicacao.porta.saida;

import br.com.javaricci.dominio.modelo.Loja;
import java.util.List;
import java.util.Optional;

/** Porta de saída para persistência de lojas. */
public interface LojaRepositorio {

    Long inserir(Loja loja);

    boolean atualizar(Loja loja);

    boolean excluir(Long id);

    List<Loja> listar();

    Optional<Loja> buscarPorId(Long id);
}
