package br.com.javaricci.apresentacao.sessao;

import br.com.javaricci.dominio.modelo.Carrinho;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/** Mantém, na sessão HTTP, um carrinho independente para cada loja. */
@Component
@SessionScope
public class CarrinhoSessao implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Long, Carrinho> carrinhosPorLoja = new HashMap<>();

    public Carrinho obterCarrinho(Long lojaId) {
        return carrinhosPorLoja.computeIfAbsent(lojaId, id -> new Carrinho());
    }

    public void descartarCarrinho(Long lojaId) {
        carrinhosPorLoja.remove(lojaId);
    }
}
