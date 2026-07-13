package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.mapper.RestauranteExpedienteMapper;
import br.com.fiap.restaurant_management.core.usecase.RestauranteExpedienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteExpedienteControllerTest {

    @Mock
    private RestauranteExpedienteUseCase restauranteExpedienteUseCase;

    @Mock
    private RestauranteExpedienteMapper restauranteExpedienteMapper;

    @InjectMocks
    private RestauranteExpedienteController controller;

    private UUID id;
    private Long idRestaurante;
    private RestauranteExpedienteDTO dto;
    private RestauranteExpediente domain;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        idRestaurante = 1L;
        dto = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
        domain = new RestauranteExpediente(id, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
    }

    @Test
    void criarExpedienteDeveDelegarParaUseCaseERetornarDto() {
        when(restauranteExpedienteMapper.toDomain(dto)).thenReturn(domain);
        when(restauranteExpedienteUseCase.create(domain)).thenReturn(domain);
        when(restauranteExpedienteMapper.toDTO(domain)).thenReturn(dto);

        RestauranteExpedienteDTO resultado = controller.criarExpediente(dto);

        assertThat(resultado).isEqualTo(dto);
        verify(restauranteExpedienteUseCase, times(1)).create(domain);
    }

    @Test
    void buscarPorIdDeveDelegarParaUseCaseERetornarDto() {
        when(restauranteExpedienteUseCase.findById(id)).thenReturn(domain);
        when(restauranteExpedienteMapper.toDTO(domain)).thenReturn(dto);

        RestauranteExpedienteDTO resultado = controller.buscarPorId(id);

        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void listarPorRestauranteDeveRetornarListaDeDtos() {
        when(restauranteExpedienteUseCase.findByRestaurante(idRestaurante)).thenReturn(List.of(domain));
        when(restauranteExpedienteMapper.toDTO(domain)).thenReturn(dto);

        List<RestauranteExpedienteDTO> resultado = controller.listarPorRestaurante(idRestaurante);

        assertThat(resultado).containsExactly(dto);
    }

    @Test
    void atualizarExpedienteDeveDelegarParaUseCaseERetornarDto() {
        when(restauranteExpedienteMapper.toDomain(dto)).thenReturn(domain);
        when(restauranteExpedienteUseCase.update(id, domain)).thenReturn(domain);
        when(restauranteExpedienteMapper.toDTO(domain)).thenReturn(dto);

        RestauranteExpedienteDTO resultado = controller.atualizarExpediente(id, dto);

        assertThat(resultado).isEqualTo(dto);
        verify(restauranteExpedienteUseCase, times(1)).update(id, domain);
    }

    @Test
    void deletarExpedienteDeveDelegarParaUseCase() {
        controller.deletarExpediente(id);

        verify(restauranteExpedienteUseCase, times(1)).delete(id);
    }
}
