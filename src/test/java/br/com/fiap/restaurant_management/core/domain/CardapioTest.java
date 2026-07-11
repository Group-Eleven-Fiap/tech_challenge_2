package br.com.fiap.restaurant_management.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CardapioTest {

    @Test
    void criar_comDadosValidos_deveCriar() {
        Cardapio c = new Cardapio("Pizza", "Delicious", new BigDecimal("10.00"), true, "http://img.png");
        assertNotNull(c);
        assertEquals("Pizza", c.getNome());
        assertEquals("Delicious", c.getDescricao());
        assertEquals(new BigDecimal("10.00"), c.getPreco());
        assertTrue(c.getDisponibilidadeRestaurante());
        assertEquals("http://img.png", c.getFotoUrl());
    }

    @Test
    void criar_nomeNulo_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio(null, "Desc", new BigDecimal("1.00"), true, "u"));
    }

    @Test
    void criar_nomeBlank_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("   ", "Desc", new BigDecimal("1.00"), true, "u"));
    }

    @Test
    void criar_descricaoNula_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", null, new BigDecimal("1.00"), true, "u"));
    }

    @Test
    void criar_descricaoBlank_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "", new BigDecimal("1.00"), true, "u"));
    }

    @Test
    void criar_precoNulo_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "D", null, true, "u"));
    }

    @Test
    void criar_precoZeroOuNegativo_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "D", BigDecimal.ZERO, true, "u"));
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "D", new BigDecimal("-1.00"), true, "u"));
    }

    @Test
    void criar_disponibilidadeNula_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "D", new BigDecimal("1.00"), null, "u"));
    }

    @Test
    void criar_fotoUrlNulaOuBlank_deveLancar() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "D", new BigDecimal("1.00"), true, null));
        assertThrows(IllegalArgumentException.class, () ->
                new Cardapio("N", "D", new BigDecimal("1.00"), true, "   "));
    }
}
