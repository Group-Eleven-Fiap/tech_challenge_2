package br.com.fiap.restaurant_management;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.CardapioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.TipoUsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.CardapioRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.TipoUsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardapioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private RestauranteEntity restaurante;

    private CardapioEntity cardapio;

    @BeforeEach
    void setUp() {
        cardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();

        var tipoUsuario = tipoUsuarioRepository.save(TipoUsuarioEntity.builder()
                .nome("Proprietário")
                .build());

        var dono = usuarioRepository.save(UsuarioEntity.builder()
                .nome("DONO_TESTE")
                .email("dono@teste.com")
                .login("dono_teste")
                .senha("senha")
                .tipoUsuario(tipoUsuario)
                .build());

        restaurante = restauranteRepository.save(RestauranteEntity.builder()
                .nome("Restaurante Teste")
                .endereco("Endereço Teste")
                .tipoCozinha("Brasileira")
                .dono(dono)
                .build());

        cardapio = cardapioRepository.save(CardapioEntity.builder()
                .restaurante(restaurante)
                .nome("Bife à Parmesana")
                .descricao("Suculento bife bovino empanado com molho de tomate caseiro e queijo derretido. Acompanha arroz e batata frita.")
                .preco(BigDecimal.valueOf(49.90))
                .disponibilidadeRestaurante(true)
                .fotoUrl("https://example.com/images/bife-parmesana.jpg")
                .build());

    }

    @Test
    void deveCriarCardapio() throws Exception {

        mockMvc.perform(post("/v1/cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardapioJson(restaurante.getId())))
                .andExpect(status().isCreated());
    }

    @Test
    void deveAtualizarCardapio() throws Exception {

        mockMvc.perform(put("/v1/cardapio/{id}", cardapio.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardapioJson(restaurante.getId())))
                .andExpect(status().isOk());
    }

    @Test
    void deveListarCardapio() throws Exception {

        mockMvc.perform(get("/v1/cardapio"))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarCardapio() throws Exception {

        mockMvc.perform(delete("/v1/cardapio/{id}", cardapio.getId()))
                .andExpect(status().isNoContent());
    }

    private String cardapioJson(Long idRestaurante) {
        return """
                {
                    "idRestaurante": %d,
                    "nome": "Bife à Parmesana",
                    "descricao": "Suculento bife bovino empanado com molho de tomate caseiro e queijo derretido. Acompanha arroz e batata frita.",
                    "preco": 49.90,
                    "disponibilidadeRestaurante": true,
                    "fotoUrl": "https://example.com/images/bife-parmesana.jpg"
                }
                """.formatted(idRestaurante);
    }

}
