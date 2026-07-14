package br.com.fiap.restaurant_management;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteExpedienteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteExpedienteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestauranteExpedienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteExpedienteRepository restauranteExpedienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private RestauranteEntity restaurante;

    @BeforeEach
    void setUp() {
        restauranteExpedienteRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();

        var dono = usuarioRepository.save(UsuarioEntity.builder()
                .nome("DONO_TESTE")
                .email("dono@teste.com")
                .login("dono_teste")
                .senha("senha")
                .build());

        restaurante = restauranteRepository.save(RestauranteEntity.builder()
                .nome("Restaurante Teste")
                .endereco("Endereço Teste")
                .tipoCozinha("Brasileira")
                .dono(dono)
                .build());
    }

    @Test
    void deveCriarEBuscarExpediente() throws Exception {
        // Create
        mockMvc.perform(post("/v1/restaurante-expediente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expedienteJson(restaurante.getId(), "SEGUNDA", "08:00:00", "18:00:00")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diaSemana").value("SEGUNDA"));

        var expediente = restauranteExpedienteRepository.findAll().getFirst();

        // Find by Id
        mockMvc.perform(get("/v1/restaurante-expediente/{id}", expediente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expediente.getId().toString()));

        // Find by Restaurante
        mockMvc.perform(get("/v1/restaurante-expediente")
                        .param("idRestaurante", restaurante.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void deveAtualizarExpediente() throws Exception {
        var expediente = restauranteExpedienteRepository.save(
                RestauranteExpedienteEntity.builder()
                        .restaurante(restaurante)
                        .diaSemana("SEGUNDA")
                        .horaAbertura(LocalTime.of(8, 0))
                        .horaFechamento(LocalTime.of(18, 0))
                        .build()
        );

        mockMvc.perform(put("/v1/restaurante-expediente/{id}", expediente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expedienteJson(restaurante.getId(), "TERCA", "09:00:00", "19:00:00")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diaSemana").value("TERCA"))
                .andExpect(jsonPath("$.horaAbertura").value("09:00:00"));
    }

    @Test
    void deveDeletarExpediente() throws Exception {
        var expediente = restauranteExpedienteRepository.save(
                RestauranteExpedienteEntity.builder()
                        .restaurante(restaurante)
                        .diaSemana("QUARTA")
                        .horaAbertura(LocalTime.of(10, 0))
                        .horaFechamento(LocalTime.of(20, 0))
                        .build()
        );

        mockMvc.perform(delete("/v1/restaurante-expediente/{id}", expediente.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/restaurante-expediente/{id}", expediente.getId()))
                .andExpect(status().isBadRequest());
    }

    private String expedienteJson(Long idRestaurante, String diaSemana, String horaAbertura, String horaFechamento) {
        return """
                {
                  "idRestaurante": %d,
                  "diaSemana": "%s",
                  "horaAbertura": "%s",
                  "horaFechamento": "%s"
                }
                """.formatted(idRestaurante, diaSemana, horaAbertura, horaFechamento);
    }
}
