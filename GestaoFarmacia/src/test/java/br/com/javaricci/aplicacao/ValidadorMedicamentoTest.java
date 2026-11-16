package br.com.javaricci.aplicacao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.javaricci.aplicacao.validacao.ValidadorMedicamento;
import br.com.javaricci.dominio.excecao.RegraNegocioException;
import br.com.javaricci.dominio.modelo.Medicamento;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ValidadorMedicamentoTest {

    private final ValidadorMedicamento validador = new ValidadorMedicamento();

    @Test
    void deveAceitarMedicamentoValido() {
        assertDoesNotThrow(() -> validador.validar(criar(10, "5.00", LocalDate.of(2025, 1, 1), LocalDate.of(2027, 1, 1))));
    }

    @Test
    void deveRejeitarValidadeAnteriorAFabricacao() {
        Medicamento invalido = criar(10, "5.00", LocalDate.of(2025, 1, 1), LocalDate.of(2024, 1, 1));
        assertThrows(RegraNegocioException.class, () -> validador.validar(invalido));
    }

    @Test
    void deveRejeitarQuantidadeNegativa() {
        Medicamento invalido = criar(-1, "5.00", LocalDate.of(2025, 1, 1), LocalDate.of(2027, 1, 1));
        assertThrows(RegraNegocioException.class, () -> validador.validar(invalido));
    }

    private Medicamento criar(int quantidade, String preco, LocalDate fabricacao, LocalDate validade) {
        return new Medicamento(null, 1L, "Teste", "Tipo", "Composição", "Descrição",
                quantidade, new BigDecimal(preco), fabricacao, validade);
    }
}
