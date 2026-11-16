package br.com.javaricci.aplicacao.validacao;

import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Medicamento;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/** Regras de negócio aplicadas ao cadastro de medicamentos. */
@Component
public class ValidadorMedicamento {

    public void validar(Medicamento medicamento) {
        if (medicamento.getQuantidadeDisponivel() < 0) {
            throw new RegraNegocioException("A quantidade disponível não pode ser negativa.");
        }
        if (medicamento.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("O preço não pode ser negativo.");
        }
        if (medicamento.getDataValidade().isBefore(medicamento.getDataFabricacao())) {
            throw new RegraNegocioException("A data de validade não pode ser anterior à data de fabricação.");
        }
    }
}
