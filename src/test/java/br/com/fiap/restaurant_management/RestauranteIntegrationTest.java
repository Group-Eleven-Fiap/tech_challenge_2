package br.com.fiap.restaurant_management;

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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestauranteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
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
    void deveExecutarCrudRestEManterVinculoComExpediente() throws Exception {
        var dono = usuarioRepository.save(UsuarioEntity.builder()
                .nome("DONO")
                .email("DONO@EMAIL.COM")
                .login("DONO_INTEGRACAO")
                .senha("SENHA")
                .build());

        mockMvc.perform(post("/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restauranteJson("Sabor Brasil", "Rua A, 10", dono.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Sabor Brasil"))
                .andExpect(jsonPath("$.horarioFuncionamento").doesNotExist());

        var restaurante = restauranteRepository.findAll().getFirst();

        mockMvc.perform(post("/v1/restaurante-expediente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expedienteJson(restaurante.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRestaurante").value(restaurante.getId()))
                .andExpect(jsonPath("$.diaSemana").value("SEGUNDA"));

        var expediente = restauranteExpedienteRepository.findAll().getFirst();
        org.junit.jupiter.api.Assertions.assertEquals(
                restaurante.getId(), expediente.getRestaurante().getId());

        mockMvc.perform(get("/v1/restaurantes/{id}", restaurante.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoCozinha").value("Brasileira"));

        mockMvc.perform(get("/v1/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(put("/v1/restaurantes/{id}", restaurante.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restauranteJson("Sabor Brasileiro", "Rua B, 20", dono.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Sabor Brasileiro"));

        mockMvc.perform(delete("/v1/restaurantes/{id}", restaurante.getId()))
                .andExpect(status().isNoContent());

        org.junit.jupiter.api.Assertions.assertEquals(0, restauranteExpedienteRepository.count());

        mockMvc.perform(get("/v1/restaurantes/{id}", restaurante.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveExporDocumentacaoSwaggerDosRestaurantes() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
                        .string(containsString("/v1/restaurantes")))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
                        .string(containsString("Cadastrar restaurante")));
    }

    private String restauranteJson(String nome, String endereco, Long idDono) {
        return """
                {
                  "nome": "%s",
                  "endereco": "%s",
                  "tipoCozinha": "Brasileira",
                  "idDono": %d
                }
                """.formatted(nome, endereco, idDono);
    }

    private String expedienteJson(Long idRestaurante) {
        return """
                {
                  "idRestaurante": %d,
                  "diaSemana": "SEGUNDA",
                  "horaAbertura": "11:00:00",
                  "horaFechamento": "22:00:00"
                }
                """.formatted(idRestaurante);
    }
}
