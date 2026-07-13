package br.com.fiap.restaurant_management;

import br.com.fiap.restaurant_management.core.controller.RestauranteController;
import br.com.fiap.restaurant_management.core.controller.RestauranteExpedienteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteExpedienteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RestauranteIntegrationTest {

    @Autowired
    private RestauranteController restauranteController;
    @Autowired
    private RestauranteExpedienteController restauranteExpedienteController;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private RestauranteExpedienteRepository restauranteExpedienteRepository;

    @BeforeEach
    void limparBanco() {
        restauranteExpedienteRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void deveExecutarFluxoCompletoComBancoDeDados() {
        var dono = usuarioRepository.save(UsuarioEntity.builder()
                .nome("DONO")
                .email("DONO@EMAIL.COM")
                .login("DONO_INTEGRACAO")
                .senha("SENHA")
                .build());

        var criado = restauranteController.criar(new RestauranteDTO(
                null, "Sabor Brasil", "Rua A, 10", "Brasileira", dono.getId(), null, null));

        assertNotNull(criado.getId());
        assertEquals(1, restauranteController.consultarTodos().size());
        assertEquals("Sabor Brasil", restauranteController.consultarPorId(criado.getId()).getNome());

        var expediente = restauranteExpedienteController.criarExpediente(new RestauranteExpedienteDTO(
                null, criado.getId(), "SEGUNDA", LocalTime.of(11, 0), LocalTime.of(22, 0)));
        assertNotNull(expediente.getId());
        assertEquals(criado.getId(), restauranteExpedienteRepository.findById(expediente.getId())
                .orElseThrow().getRestaurante().getId());

        var atualizado = restauranteController.atualizar(criado.getId(), new RestauranteDTO(
                null, "Sabor Brasileiro", "Rua B, 20", "Brasileira", dono.getId(), null, null));
        assertEquals("Sabor Brasileiro", atualizado.getNome());
        assertEquals("Rua B, 20", restauranteRepository.findById(criado.getId()).orElseThrow().getEndereco());

        restauranteController.excluir(criado.getId());
        assertEquals(0, restauranteExpedienteRepository.count());
        assertThrows(ResourceNotFoundException.class,
                () -> restauranteController.consultarPorId(criado.getId()));
        usuarioRepository.deleteById(dono.getId());
    }
}
