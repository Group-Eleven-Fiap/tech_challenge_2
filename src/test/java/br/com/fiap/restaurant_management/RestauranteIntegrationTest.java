package br.com.fiap.restaurant_management;

import br.com.fiap.restaurant_management.core.controller.RestauranteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class RestauranteIntegrationTest {

    @Autowired
    private RestauranteController restauranteController;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    void deveExecutarFluxoCompletoComBancoDeDados() {
        var dono = usuarioRepository.save(UsuarioEntity.builder()
                .nome("DONO")
                .email("DONO@EMAIL.COM")
                .login("DONO_INTEGRACAO")
                .senha("SENHA")
                .build());

        var criado = restauranteController.criar(new RestauranteDTO(
                null, "Sabor Brasil", "Rua A, 10", "Brasileira", "11h às 22h",
                dono.getId(), null, null));

        assertNotNull(criado.getId());
        assertEquals(1, restauranteController.consultarTodos().size());
        assertEquals("Sabor Brasil", restauranteController.consultarPorId(criado.getId()).getNome());

        var atualizado = restauranteController.atualizar(criado.getId(), new RestauranteDTO(
                null, "Sabor Brasileiro", "Rua B, 20", "Brasileira", "12h às 23h",
                dono.getId(), null, null));
        assertEquals("Sabor Brasileiro", atualizado.getNome());
        assertEquals("Rua B, 20", restauranteRepository.findById(criado.getId()).orElseThrow().getEndereco());

        restauranteController.excluir(criado.getId());
        assertThrows(ResourceNotFoundException.class,
                () -> restauranteController.consultarPorId(criado.getId()));
    }
}
