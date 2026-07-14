package br.com.fiap.restaurant_management;

import br.com.fiap.restaurant_management.infra.database.jpa.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TipoUsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TipoUsuarioRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

    }

    @Test
    void deveCriarTipoUsuario() throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Dono")))
                .andExpect(status().isOk());

        var tiposUsuario = repository.findAll();

        assertEquals(1, tiposUsuario.size());
        assertEquals("DONO", tiposUsuario.getFirst().getNome());
    }

    @Test
    void deveConsultarTiposUsuario() throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Dono")))
                .andExpect(status().isOk());

        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Cliente")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/tipo-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nome").exists())
                .andExpect(jsonPath("$[*].nome").isArray());
    }

    @Test
    void deveAtualizarTipoUsuario() throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Dono")))
                .andExpect(status().isOk());

        var tipoUsuario = repository.findAll().getFirst();

        mockMvc.perform(put("/v1/tipo-usuario/{id}", tipoUsuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Administrador")))
                .andExpect(status().isOk());

        var tipoUsuarioAtualizado = repository.findById(tipoUsuario.getId());

        assertTrue(tipoUsuarioAtualizado.isPresent());
        assertEquals("ADMINISTRADOR", tipoUsuarioAtualizado.get().getNome());
    }

    @Test
    void deveExcluirTipoUsuario() throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Cliente")))
                .andExpect(status().isOk());

        var tipoUsuario = repository.findAll().getFirst();

        mockMvc.perform(delete("/v1/tipo-usuario/{id}", tipoUsuario.getId()))
                .andExpect(status().isOk());

        assertFalse(repository.existsById(tipoUsuario.getId()));
        assertEquals(0, repository.count());
    }

    @Test
    void deveExecutarCrudTipoUsuario() throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Dono")))
                .andExpect(status().isOk());

        var tipoUsuario = repository.findAll().getFirst();

        assertEquals("DONO", tipoUsuario.getNome());

        mockMvc.perform(get("/v1/tipo-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(tipoUsuario.getId()))
                .andExpect(jsonPath("$[0].nome").value("DONO"));

        mockMvc.perform(put("/v1/tipo-usuario/{id}", tipoUsuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("Cliente")))
                .andExpect(status().isOk());

        var tipoUsuarioAtualizado = repository.findById(tipoUsuario.getId());

        assertTrue(tipoUsuarioAtualizado.isPresent());
        assertEquals("CLIENTE", tipoUsuarioAtualizado.get().getNome());

        mockMvc.perform(delete("/v1/tipo-usuario/{id}", tipoUsuario.getId()))
                .andExpect(status().isOk());

        assertEquals(0, repository.count());
    }

    @Test
    void deveRetornarBadRequestAoCriarTipoUsuarioSemNome() throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson("")))
                .andExpect(status().isBadRequest());

        assertEquals(0, repository.count());
    }

    private String tipoUsuarioJson(String nome) {
        return """
                {
                  "nome": "%s"
                }
                """.formatted(nome);
    }


}
