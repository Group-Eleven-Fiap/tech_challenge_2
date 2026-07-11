package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.RestauranteExpedienteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteExpedienteInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteExpedienteApiControllerTest {

    @Mock
    private RestauranteExpedienteController restauranteExpedienteController;

    @InjectMocks
    private RestauranteExpedienteApiController apiController;

    private UUID id;
    private UUID idRestaurante;
    private RestauranteExpedienteInput input;
    private RestauranteExpedienteDTO dtoRetornado;

    @BeforeEach
    void setUp() throws Exception {
        id = UUID.randomUUID();
        idRestaurante = UUID.randomUUID();
        input = criarInput(idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
        dtoRetornado = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
    }

    @Test
    void createDeveDelegarParaOControllerComIdNulo() {
        when(restauranteExpedienteController.criarExpediente(any())).thenReturn(dtoRetornado);

        RestauranteExpedienteDTO resultado = apiController.create(input);

        assertThat(resultado).isEqualTo(dtoRetornado);
        verify(restauranteExpedienteController, times(1)).criarExpediente(
                argThat(dto -> dto.getId() == null && dto.getIdRestaurante().equals(idRestaurante)));
    }

    @Test
    void findByIdDeveDelegarParaOController() {
        when(restauranteExpedienteController.buscarPorId(id)).thenReturn(dtoRetornado);

        RestauranteExpedienteDTO resultado = apiController.findById(id);

        assertThat(resultado).isEqualTo(dtoRetornado);
    }

    @Test
    void findByRestauranteDeveDelegarParaOController() {
        when(restauranteExpedienteController.listarPorRestaurante(idRestaurante)).thenReturn(List.of(dtoRetornado));

        List<RestauranteExpedienteDTO> resultado = apiController.findByRestaurante(idRestaurante);

        assertThat(resultado).containsExactly(dtoRetornado);
    }

    @Test
    void updateDeveDelegarParaOControllerComIdDoPath() {
        when(restauranteExpedienteController.atualizarExpediente(eq(id), any())).thenReturn(dtoRetornado);

        RestauranteExpedienteDTO resultado = apiController.update(id, input);

        assertThat(resultado).isEqualTo(dtoRetornado);
        verify(restauranteExpedienteController, times(1)).atualizarExpediente(
                eq(id), argThat(dto -> dto.getId().equals(id)));
    }

    @Test
    void deleteDeveDelegarParaOController() {
        apiController.delete(id);

        verify(restauranteExpedienteController, times(1)).deletarExpediente(id);
    }

    // RestauranteExpedienteInput só tem @NoArgsConstructor + getters (é um DTO de request),
    // então os campos são preenchidos via reflection para simular o binding do Jackson nos testes.
    private RestauranteExpedienteInput criarInput(UUID idRestaurante, String diaSemana, LocalTime abertura, LocalTime fechamento) throws Exception {
        RestauranteExpedienteInput novoInput = new RestauranteExpedienteInput();
        setField(novoInput, "idRestaurante", idRestaurante);
        setField(novoInput, "diaSemana", diaSemana);
        setField(novoInput, "horaAbertura", abertura);
        setField(novoInput, "horaFechamento", fechamento);
        return novoInput;
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
