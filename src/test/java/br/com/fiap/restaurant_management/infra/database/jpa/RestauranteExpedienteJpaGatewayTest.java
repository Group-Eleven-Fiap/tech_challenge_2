package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteExpedienteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteExpedienteRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.RestauranteExpedienteEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteExpedienteJpaGatewayTest {

    @Mock
    private RestauranteExpedienteEntityMapper restauranteExpedienteEntityMapper;

    @Mock
    private RestauranteExpedienteRepository restauranteExpedienteRepository;

    @InjectMocks
    private RestauranteExpedienteJpaGateway gateway;

    private UUID id;
    private UUID idRestaurante;
    private RestauranteExpedienteDTO dto;
    private RestauranteExpedienteEntity entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        idRestaurante = UUID.randomUUID();
        dto = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
        entity = RestauranteExpedienteEntity.builder()
                .id(id)
                .idRestaurante(idRestaurante)
                .diaSemana("SEGUNDA")
                .horaAbertura(LocalTime.of(8, 0))
                .horaFechamento(LocalTime.of(18, 0))
                .build();
    }

    @Test
    void createExpedienteDeveSalvarEDevolverDto() {
        when(restauranteExpedienteEntityMapper.toEntity(dto)).thenReturn(entity);
        when(restauranteExpedienteRepository.save(entity)).thenReturn(entity);
        when(restauranteExpedienteEntityMapper.toDTO(entity)).thenReturn(dto);

        RestauranteExpedienteDTO resultado = gateway.createExpediente(dto);

        assertThat(resultado).isEqualTo(dto);
        verify(restauranteExpedienteRepository, times(1)).save(entity);
    }

    @Test
    void findByIdDeveRetornarOptionalPreenchidoQuandoEncontrado() {
        when(restauranteExpedienteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restauranteExpedienteEntityMapper.toDTO(entity)).thenReturn(dto);

        Optional<RestauranteExpedienteDTO> resultado = gateway.findById(id);

        assertThat(resultado).contains(dto);
    }

    @Test
    void findByIdDeveRetornarOptionalVazioQuandoNaoEncontrado() {
        when(restauranteExpedienteRepository.findById(id)).thenReturn(Optional.empty());

        Optional<RestauranteExpedienteDTO> resultado = gateway.findById(id);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findByRestauranteDeveRetornarListaDeDtos() {
        when(restauranteExpedienteRepository.findByIdRestaurante(idRestaurante)).thenReturn(List.of(entity));
        when(restauranteExpedienteEntityMapper.toDTO(entity)).thenReturn(dto);

        List<RestauranteExpedienteDTO> resultado = gateway.findByRestaurante(idRestaurante);

        assertThat(resultado).containsExactly(dto);
    }

    @Test
    void existsByRestauranteAndDiaDeveDelegarParaORepository() {
        when(restauranteExpedienteRepository.existsByIdRestauranteAndDiaSemanaIgnoreCase(idRestaurante, "SEGUNDA")).thenReturn(true);

        boolean resultado = gateway.existsByRestauranteAndDia(idRestaurante, "SEGUNDA");

        assertThat(resultado).isTrue();
    }

    @Test
    void updateExpedienteDeveSalvarQuandoExistir() {
        when(restauranteExpedienteRepository.existsById(id)).thenReturn(true);
        when(restauranteExpedienteEntityMapper.toEntity(dto)).thenReturn(entity);
        when(restauranteExpedienteRepository.save(entity)).thenReturn(entity);
        when(restauranteExpedienteEntityMapper.toDTO(entity)).thenReturn(dto);

        RestauranteExpedienteDTO resultado = gateway.updateExpediente(dto);

        assertThat(resultado).isEqualTo(dto);
    }

    @Test
    void updateExpedienteDeveLancarExcecaoQuandoNaoExistir() {
        when(restauranteExpedienteRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> gateway.updateExpediente(dto))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("expediente não encontrado");

        verify(restauranteExpedienteRepository, times(0)).save(any());
    }

    @Test
    void deleteByIdDeveDelegarParaORepository() {
        gateway.deleteById(id);

        verify(restauranteExpedienteRepository, times(1)).deleteById(id);
    }
}
