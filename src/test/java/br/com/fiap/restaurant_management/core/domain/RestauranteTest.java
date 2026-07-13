package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestauranteTest {

    @Test
    void deveCriarRestauranteEEliminarEspacosExternos() {
        var restaurante = new Restaurante(
                "  Sabor Brasil  ", "  Rua A, 10  ", "  Brasileira  ", "  11h às 22h  ", 1L);

        assertEquals("Sabor Brasil", restaurante.getNome());
        assertEquals("Rua A, 10", restaurante.getEndereco());
        assertEquals("Brasileira", restaurante.getTipoCozinha());
        assertEquals("11h às 22h", restaurante.getHorarioFuncionamento());
        assertEquals(1L, restaurante.getIdDono());
    }

    @Test
    void deveRejeitarCamposTextuaisVazios() {
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante(" ", "Rua A", "Brasileira", "11h às 22h", 1L));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", null, "Brasileira", "11h às 22h", 1L));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", " ", "11h às 22h", 1L));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", "Brasileira", "", 1L));
    }

    @Test
    void deveRejeitarDonoInvalido() {
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", "Brasileira", "11h às 22h", null));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", "Brasileira", "11h às 22h", 0L));
    }
}
