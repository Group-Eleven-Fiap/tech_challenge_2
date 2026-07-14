package br.com.fiap.restaurant_management;

import br.com.fiap.restaurant_management.infra.database.jpa.repository.TipoUsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
    }

    @Test
    void deveCriarUsuario() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Dono");

        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "Nome Teste",
                                "email.teste@email.com",
                                "login.teste",
                                "senha.teste",
                                tipoUsuarioId)))
                .andExpect(status().isOk());

        var usuarios = usuarioRepository.findAll();

        assertEquals(1, usuarios.size());
        assertEquals("NOME TESTE", usuarios.getFirst().getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", usuarios.getFirst().getEmail());
        assertEquals("LOGIN.TESTE", usuarios.getFirst().getLogin());
        assertEquals("SENHA.TESTE", usuarios.getFirst().getSenha());
        assertNotNull(usuarios.getFirst().getCriadoEm());
        assertNotNull(usuarios.getFirst().getTipoUsuario());
        assertEquals(tipoUsuarioId, usuarios.getFirst().getTipoUsuario().getId());
    }

    @Test
    void deveConsultarUsuarios() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Cliente");

        criarUsuario(
                "Cliente Teste",
                "cliente.teste@email.com",
                "cliente.teste",
                "senha.teste",
                tipoUsuarioId
        );

        criarUsuario(
                "Usuario Teste",
                "usuario.teste@email.com",
                "usuario.teste",
                "senha.teste",
                tipoUsuarioId
        );

        mockMvc.perform(get("/v1/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nome").exists())
                .andExpect(jsonPath("$[0].email").exists())
                .andExpect(jsonPath("$[0].login").exists())
                .andExpect(jsonPath("$[0].tipoUsuario").exists());
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Dono");

        var usuarioId = criarUsuario(
                "Nome Antigo",
                "email.antigo@email.com",
                "login.antigo",
                "senha.antiga",
                tipoUsuarioId
        );

        mockMvc.perform(put("/v1/usuario/{id}", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "Nome Atualizado",
                                "email.atualizado@email.com",
                                "login.atualizado",
                                "senha.atualizada",
                                tipoUsuarioId)))
                .andExpect(status().isOk());

        var usuarioAtualizado = usuarioRepository.findById(usuarioId);

        assertTrue(usuarioAtualizado.isPresent());
        assertEquals("NOME ATUALIZADO", usuarioAtualizado.get().getNome());
        assertEquals("EMAIL.ATUALIZADO@EMAIL.COM", usuarioAtualizado.get().getEmail());
        assertEquals("LOGIN.ATUALIZADO", usuarioAtualizado.get().getLogin());
        assertEquals("SENHA.ATUALIZADA", usuarioAtualizado.get().getSenha());
        assertNotNull(usuarioAtualizado.get().getAtualizadoEm());
        assertEquals(tipoUsuarioId, usuarioAtualizado.get().getTipoUsuario().getId());
    }

    @Test
    void deveExcluirUsuario() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Cliente");

        var usuarioId = criarUsuario(
                "Usuario Excluir",
                "usuario.excluir@email.com",
                "usuario.excluir",
                "senha.excluir",
                tipoUsuarioId
        );

        mockMvc.perform(delete("/v1/usuario/{id}", usuarioId))
                .andExpect(status().isOk());

        assertFalse(usuarioRepository.existsById(usuarioId));
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void deveAtualizarTipoUsuarioDoUsuario() throws Exception {
        var tipoUsuarioAtualId = criarTipoUsuario("Dono");
        var novoTipoUsuarioId = criarTipoUsuario("Cliente");

        var usuarioId = criarUsuario(
                "Usuario Tipo",
                "usuario.tipo@email.com",
                "usuario.tipo",
                "senha.tipo",
                tipoUsuarioAtualId
        );

        mockMvc.perform(patch("/v1/usuario/{usuarioId}", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioTipoUsuarioJson(usuarioId, novoTipoUsuarioId)))
                .andExpect(status().isOk());

        var usuarioAtualizado = usuarioRepository.findById(usuarioId);

        assertTrue(usuarioAtualizado.isPresent());
        assertNotNull(usuarioAtualizado.get().getTipoUsuario());
        assertEquals(novoTipoUsuarioId, usuarioAtualizado.get().getTipoUsuario().getId());
    }

    @Test
    void deveExecutarCrudUsuario() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Dono");

        var usuarioId = criarUsuario(
                "Usuario Crud",
                "usuario.crud@email.com",
                "usuario.crud",
                "senha.crud",
                tipoUsuarioId
        );

        var usuario = usuarioRepository.findById(usuarioId);

        assertTrue(usuario.isPresent());
        assertEquals("USUARIO CRUD", usuario.get().getNome());

        mockMvc.perform(get("/v1/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(usuarioId))
                .andExpect(jsonPath("$[0].nome").value("USUARIO CRUD"))
                .andExpect(jsonPath("$[0].email").value("USUARIO.CRUD@EMAIL.COM"))
                .andExpect(jsonPath("$[0].login").value("USUARIO.CRUD"));

        mockMvc.perform(put("/v1/usuario/{id}", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "Usuario Crud Atualizado",
                                "usuario.crud.atualizado@email.com",
                                "usuario.crud.atualizado",
                                "senha.crud.atualizada",
                                tipoUsuarioId)))
                .andExpect(status().isOk());

        var usuarioAtualizado = usuarioRepository.findById(usuarioId);

        assertTrue(usuarioAtualizado.isPresent());
        assertEquals("USUARIO CRUD ATUALIZADO", usuarioAtualizado.get().getNome());
        assertEquals("USUARIO.CRUD.ATUALIZADO@EMAIL.COM", usuarioAtualizado.get().getEmail());
        assertEquals("USUARIO.CRUD.ATUALIZADO", usuarioAtualizado.get().getLogin());

        mockMvc.perform(delete("/v1/usuario/{id}", usuarioId))
                .andExpect(status().isOk());

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void deveRetornarBadRequestAoCriarUsuarioSemNome() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Cliente");

        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "",
                                "usuario.validacao@email.com",
                                "usuario.validacao",
                                "senha.validacao",
                                tipoUsuarioId)))
                .andExpect(status().isBadRequest());

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void deveRetornarBadRequestAoCriarUsuarioSemEmail() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Cliente");

        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "Usuario Validacao",
                                "",
                                "usuario.validacao.email",
                                "senha.validacao",
                                tipoUsuarioId)))
                .andExpect(status().isBadRequest());

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void deveRetornarBadRequestAoCriarUsuarioSemLogin() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Cliente");

        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "Usuario Validacao",
                                "usuario.validacao.login@email.com",
                                "",
                                "senha.validacao",
                                tipoUsuarioId)))
                .andExpect(status().isBadRequest());

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void deveRetornarBadRequestAoCriarUsuarioSemSenha() throws Exception {
        var tipoUsuarioId = criarTipoUsuario("Cliente");

        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(
                                "Usuario Validacao",
                                "usuario.validacao.senha@email.com",
                                "usuario.validacao.senha",
                                "",
                                tipoUsuarioId)))
                .andExpect(status().isBadRequest());

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void deveRetornarBadRequestAoCriarUsuarioSemTipoUsuario() throws Exception {
        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Usuario Validacao",
                                  "email": "usuario.validacao.tipo@email.com",
                                  "login": "usuario.validacao.tipo",
                                  "senha": "senha.validacao"
                                }
                                """))
                .andExpect(status().isBadRequest());

        assertEquals(0, usuarioRepository.count());
    }

    private Long criarTipoUsuario(String nome) throws Exception {
        mockMvc.perform(post("/v1/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tipoUsuarioJson(nome)))
                .andExpect(status().isOk());

        return tipoUsuarioRepository.findAll().getLast().getId();
    }

    private Long criarUsuario(String nome, String email, String login, String senha, Long tipoUsuarioId
    ) throws Exception {
        mockMvc.perform(post("/v1/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson(nome, email, login, senha, tipoUsuarioId)))
                .andExpect(status().isOk());

        return usuarioRepository.findAll().getLast().getId();
    }

    private String tipoUsuarioJson(String nome) {
        return """
                {
                  "nome": "%s"
                }
                """.formatted(nome);
    }

    private String usuarioJson(String nome, String email, String login, String senha, Long tipoUsuarioId
    ) {
        return """
                {
                  "nome": "%s",
                  "email": "%s",
                  "login": "%s",
                  "senha": "%s",
                  "tipoUsuarioId": %d
                }
                """.formatted(nome, email, login, senha, tipoUsuarioId);
    }

    private String usuarioTipoUsuarioJson(Long usuarioId, Long tipoUsuarioId) {
        return """
                {
                  "usuarioId": %d,
                  "tipoUsuarioId": %d
                }
                """.formatted(usuarioId, tipoUsuarioId);
    }

}
