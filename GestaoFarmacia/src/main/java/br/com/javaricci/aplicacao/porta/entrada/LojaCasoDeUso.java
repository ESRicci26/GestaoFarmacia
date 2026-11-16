package br.com.javaricci.aplicacao.porta.entrada;

import br.com.javaricci.dominio.modelo.Loja;
import java.util.List;

/** Casos de uso de gerenciamento de lojas. */
public interface LojaCasoDeUso {

    Long inserir(Loja loja);

    void atualizar(Loja loja);

    void excluir(Long id);

    List<Loja> listar();

    Loja buscarPorId(Long id);
}
