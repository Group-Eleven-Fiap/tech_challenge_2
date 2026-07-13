package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.RestauranteExpedienteGateway;
import br.com.fiap.restaurant_management.core.gateway.RestauranteGateway;
import br.com.fiap.restaurant_management.core.mapper.RestauranteExpedienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteExpedienteUseCaseImplTest {

    @Mock
    private RestauranteExpedienteMapper restauranteExpedienteMapper;

    @Mock
    private RestauranteExpedienteGateway restauranteExpedienteGateway;

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private RestauranteExpedienteUseCaseImpl useCase;

    private Long idRestaurante;
    private RestauranteExpediente expedienteValido;
    private RestauranteExpedienteDTO dtoValido;

    @BeforeEach
    void setUp() {
        idRestaurante = 1L;
        expedienteValido = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
        dtoValido = new RestauranteExpedienteDTO(null, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
        lenient().when(restauranteGateway.consultarPorId(idRestaurante))
                .thenReturn(Optional.of(org.mockito.Mockito.mock(br.com.fiap.restaurant_management.core.domain.Restaurante.class)));
    }

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("deve criar o expediente quando os dados sao validos e o dia esta disponivel")
        void deveCriarExpedienteComSucesso() {
            UUID novoId = UUID.randomUUID();
            var dtoRetornado = new RestauranteExpedienteDTO(novoId, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));
            var domainRetornado = new RestauranteExpediente(novoId, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            when(restauranteExpedienteGateway.existsByRestauranteAndDia(idRestaurante, "SEGUNDA")).thenReturn(false);
            when(restauranteExpedienteMapper.toDTO(expedienteValido)).thenReturn(dtoValido);
            when(restauranteExpedienteGateway.createExpediente(dtoValido)).thenReturn(dtoRetornado);
            when(restauranteExpedienteMapper.toDomain(dtoRetornado)).thenReturn(domainRetornado);

            RestauranteExpediente resultado = useCase.create(expedienteValido);

            assertThat(resultado.getId()).isEqualTo(novoId);
            verify(restauranteExpedienteGateway, times(1)).createExpediente(dtoValido);
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException quando restaurante nao for informado")
        void deveLancarExcecaoQuandoRestauranteNaoInformado() {
            var expediente = new RestauranteExpediente(null, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThatThrownBy(() -> useCase.create(expediente))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("restaurante é obrigatório");

            verify(restauranteExpedienteGateway, never()).createExpediente(any());
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException quando o dia da semana for invalido")
        void deveLancarExcecaoQuandoDiaSemanaInvalido() {
            var expediente = new RestauranteExpediente(idRestaurante, "FERIADO", LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThatThrownBy(() -> useCase.create(expediente))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("dia da semana inválido");

            verify(restauranteExpedienteGateway, never()).createExpediente(any());
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException quando o fechamento nao for depois da abertura")
        void deveLancarExcecaoQuandoHorarioInvalido() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(18, 0), LocalTime.of(8, 0));

            assertThatThrownBy(() -> useCase.create(expediente))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("horário de fechamento deve ser posterior ao horário de abertura");

            verify(restauranteExpedienteGateway, never()).createExpediente(any());
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException quando ja existir expediente para o restaurante nesse dia")
        void deveLancarExcecaoQuandoDiaJaCadastrado() {
            when(restauranteExpedienteGateway.existsByRestauranteAndDia(idRestaurante, "SEGUNDA")).thenReturn(true);

            assertThatThrownBy(() -> useCase.create(expedienteValido))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("já existe um expediente cadastrado para este restaurante neste dia da semana");

            verify(restauranteExpedienteGateway, never()).createExpediente(any());
        }

        @Test
        @DisplayName("deve rejeitar expediente quando o restaurante nao existir")
        void deveRejeitarRestauranteInexistente() {
            when(restauranteGateway.consultarPorId(idRestaurante)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> useCase.create(expedienteValido))
                    .isInstanceOf(br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException.class);

            verify(restauranteExpedienteGateway, never()).createExpediente(any());
        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @DisplayName("deve retornar o expediente quando encontrado")
        void deveRetornarExpedienteQuandoEncontrado() {
            UUID id = UUID.randomUUID();
            var dto = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            when(restauranteExpedienteGateway.findById(id)).thenReturn(Optional.of(dto));
            when(restauranteExpedienteMapper.toDomain(dto)).thenReturn(expedienteValido);

            RestauranteExpediente resultado = useCase.findById(id);

            assertThat(resultado).isEqualTo(expedienteValido);
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException quando nao encontrado")
        void deveLancarExcecaoQuandoNaoEncontrado() {
            UUID id = UUID.randomUUID();
            when(restauranteExpedienteGateway.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> useCase.findById(id))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("expediente não encontrado");
        }
    }

    @Nested
    @DisplayName("findByRestaurante")
    class FindByRestaurante {

        @Test
        @DisplayName("deve retornar a lista de expedientes de um restaurante")
        void deveRetornarListaDeExpedientes() {
            var dto = new RestauranteExpedienteDTO(UUID.randomUUID(), idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            when(restauranteExpedienteGateway.findByRestaurante(idRestaurante)).thenReturn(List.of(dto));
            when(restauranteExpedienteMapper.toDomain(dto)).thenReturn(expedienteValido);

            List<RestauranteExpediente> resultado = useCase.findByRestaurante(idRestaurante);

            assertThat(resultado).hasSize(1).containsExactly(expedienteValido);
        }

        @Test
        @DisplayName("deve retornar lista vazia quando o restaurante nao possuir expedientes")
        void deveRetornarListaVaziaQuandoSemExpedientes() {
            when(restauranteExpedienteGateway.findByRestaurante(idRestaurante)).thenReturn(List.of());

            List<RestauranteExpediente> resultado = useCase.findByRestaurante(idRestaurante);

            assertThat(resultado).isEmpty();
        }
    }

    @Nested
    @DisplayName("update")
    class Update {

        @Test
        @DisplayName("deve atualizar quando o dia da semana nao mudou, sem checar disponibilidade novamente")
        void deveAtualizarSemTrocarDia() {
            UUID id = UUID.randomUUID();
            var dtoExistente = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(7, 0), LocalTime.of(15, 0));
            var existente = new RestauranteExpediente(id, idRestaurante, "SEGUNDA", LocalTime.of(7, 0), LocalTime.of(15, 0));
            var novosDados = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(9, 0), LocalTime.of(19, 0));
            var dtoAtualizado = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(9, 0), LocalTime.of(19, 0));
            var domainAtualizado = new RestauranteExpediente(id, idRestaurante, "SEGUNDA", LocalTime.of(9, 0), LocalTime.of(19, 0));

            when(restauranteExpedienteGateway.findById(id)).thenReturn(Optional.of(dtoExistente));
            when(restauranteExpedienteMapper.toDomain(dtoExistente)).thenReturn(existente);
            when(restauranteExpedienteMapper.toDTO(any(RestauranteExpediente.class))).thenReturn(dtoAtualizado);
            when(restauranteExpedienteGateway.updateExpediente(dtoAtualizado)).thenReturn(dtoAtualizado);
            when(restauranteExpedienteMapper.toDomain(dtoAtualizado)).thenReturn(domainAtualizado);

            RestauranteExpediente resultado = useCase.update(id, novosDados);

            assertThat(resultado.getHoraAbertura()).isEqualTo(LocalTime.of(9, 0));
            verify(restauranteExpedienteGateway, never()).existsByRestauranteAndDia(any(), anyString());
        }

        @Test
        @DisplayName("deve checar disponibilidade quando o dia da semana for alterado")
        void deveChecarDisponibilidadeQuandoTrocarDia() {
            UUID id = UUID.randomUUID();
            var dtoExistente = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(7, 0), LocalTime.of(15, 0));
            var existente = new RestauranteExpediente(id, idRestaurante, "SEGUNDA", LocalTime.of(7, 0), LocalTime.of(15, 0));
            var novosDados = new RestauranteExpediente(idRestaurante, "TERCA", LocalTime.of(9, 0), LocalTime.of(19, 0));

            when(restauranteExpedienteGateway.findById(id)).thenReturn(Optional.of(dtoExistente));
            when(restauranteExpedienteMapper.toDomain(dtoExistente)).thenReturn(existente);
            when(restauranteExpedienteGateway.existsByRestauranteAndDia(idRestaurante, "TERCA")).thenReturn(true);

            assertThatThrownBy(() -> useCase.update(id, novosDados))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("já existe um expediente cadastrado para este restaurante neste dia da semana");

            verify(restauranteExpedienteGateway, never()).updateExpediente(any());
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException ao atualizar com horario invalido")
        void deveLancarExcecaoAoAtualizarComHorarioInvalido() {
            UUID id = UUID.randomUUID();
            var novosDados = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(20, 0), LocalTime.of(8, 0));

            assertThatThrownBy(() -> useCase.update(id, novosDados))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("horário de fechamento deve ser posterior ao horário de abertura");

            verify(restauranteExpedienteGateway, never()).findById(any());
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("deve deletar quando o expediente existir")
        void deveDeletarQuandoExistir() {
            UUID id = UUID.randomUUID();
            var dto = new RestauranteExpedienteDTO(id, idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            when(restauranteExpedienteGateway.findById(id)).thenReturn(Optional.of(dto));
            when(restauranteExpedienteMapper.toDomain(dto)).thenReturn(expedienteValido);

            useCase.delete(id);

            verify(restauranteExpedienteGateway, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("deve lancar BusinessRuleException ao deletar expediente inexistente, sem chamar o gateway de delecao")
        void deveLancarExcecaoAoDeletarInexistente() {
            UUID id = UUID.randomUUID();
            when(restauranteExpedienteGateway.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> useCase.delete(id))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessage("expediente não encontrado");

            verify(restauranteExpedienteGateway, never()).deleteById(any());
        }
    }
}
