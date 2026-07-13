package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.RestauranteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteApiControllerTest {

    @Mock
    private RestauranteController restauranteController;

    private RestauranteApiController apiController;
    private RestauranteInput entrada;
    private RestauranteDTO saida;

    @BeforeEach
    void preparar() throws Exception {
        apiController = new RestauranteApiController(restauranteController);
        entrada = new RestauranteInput();
        definir("nome", "Sabor Brasil");
        definir("endereco", "Rua A, 10");
        definir("tipoCozinha", "Brasileira");
        definir("idDono", 1L);
        saida = new RestauranteDTO(2L, "Sabor Brasil", "Rua A, 10", "Brasileira",
                1L, null, null);
    }

    @Test
    void deveCriarComStatus201() {
        when(restauranteController.criar(any(RestauranteDTO.class))).thenReturn(saida);

        var resposta = apiController.criar(entrada);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(2L, resposta.getBody().getId());
    }

    @Test
    void deveAtualizarConsultarEListar() {
        when(restauranteController.atualizar(any(), any())).thenReturn(saida);
        when(restauranteController.consultarPorId(2L)).thenReturn(saida);
        when(restauranteController.consultarTodos()).thenReturn(List.of(saida));

        assertEquals(HttpStatus.OK, apiController.atualizar(2L, entrada).getStatusCode());
        assertEquals(2L, apiController.consultarPorId(2L).getBody().getId());
        assertEquals(1, apiController.consultarTodos().getBody().size());
    }

    @Test
    void deveExcluirComStatus204() {
        assertEquals(HttpStatus.NO_CONTENT, apiController.excluir(2L).getStatusCode());
        verify(restauranteController).excluir(2L);
    }

    private void definir(String campo, Object valor) throws Exception {
        Field field = RestauranteInput.class.getDeclaredField(campo);
        field.setAccessible(true);
        field.set(entrada, valor);
    }
}
