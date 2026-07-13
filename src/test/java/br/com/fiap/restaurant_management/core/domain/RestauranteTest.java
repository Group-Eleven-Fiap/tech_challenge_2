package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestauranteTest {

    @Test
    void deveCriarRestauranteEEliminarEspacosExternos() {
        var restaurante = new Restaurante(
                "  Sabor Brasil  ", "  Rua A, 10  ", "  Brasileira  ", 1L);

        assertEquals("Sabor Brasil", restaurante.getNome());
        assertEquals("Rua A, 10", restaurante.getEndereco());
        assertEquals("Brasileira", restaurante.getTipoCozinha());
        assertEquals(1L, restaurante.getIdDono());
    }

    @Test
    void deveRejeitarCamposTextuaisVazios() {
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante(" ", "Rua A", "Brasileira", 1L));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", null, "Brasileira", 1L));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", " ", 1L));
    }

    @Test
    void deveRejeitarDonoInvalido() {
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", "Brasileira", null));
        assertThrows(BusinessRuleException.class,
                () -> new Restaurante("Nome", "Rua A", "Brasileira", 0L));
    }
}
