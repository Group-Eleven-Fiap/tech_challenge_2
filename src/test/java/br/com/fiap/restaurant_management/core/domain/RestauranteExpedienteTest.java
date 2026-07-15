package br.com.fiap.restaurant_management.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class RestauranteExpedienteTest {

    private final Long idRestaurante = 1L;

    @Nested
    @DisplayName("validateIdRestaurante")
    class ValidateIdRestaurante {

        @Test
        @DisplayName("deve retornar true quando o id do restaurante for informado")
        void deveRetornarTrueQuandoIdRestauranteInformado() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThat(expediente.validateIdRestaurante()).isTrue();
        }

        @Test
        @DisplayName("deve retornar false quando o id do restaurante for nulo")
        void deveRetornarFalseQuandoIdRestauranteNulo() {
            var expediente = new RestauranteExpediente(null, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThat(expediente.validateIdRestaurante()).isFalse();
        }
    }

    @Nested
    @DisplayName("validateDiaSemana")
    class ValidateDiaSemana {

        @ParameterizedTest
        @ValueSource(strings = {"SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO", "DOMINGO", "segunda", "domingo"})
        @DisplayName("deve retornar true para dias validos, inclusive minusculo")
        void deveRetornarTrueParaDiasValidos(String diaSemana) {
            var expediente = new RestauranteExpediente(idRestaurante, diaSemana, LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThat(expediente.validateDiaSemana()).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"FERIADO", "SEG", "MONDAY", ""})
        @DisplayName("deve retornar false para dias invalidos")
        void deveRetornarFalseParaDiasInvalidos(String diaSemana) {
            var expediente = new RestauranteExpediente(idRestaurante, diaSemana, LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThat(expediente.validateDiaSemana()).isFalse();
        }

        @Test
        @DisplayName("deve retornar false quando o dia da semana for nulo")
        void deveRetornarFalseQuandoDiaSemanaNulo() {
            var expediente = new RestauranteExpediente(idRestaurante, null, LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThat(expediente.validateDiaSemana()).isFalse();
        }
    }

    @Nested
    @DisplayName("validateHorarios")
    class ValidateHorarios {

        @Test
        @DisplayName("deve retornar true quando o fechamento for depois da abertura")
        void deveRetornarTrueQuandoFechamentoDepoisDaAbertura() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(18, 0));

            assertThat(expediente.validateHorarios()).isTrue();
        }

        @Test
        @DisplayName("deve retornar false quando o fechamento for igual a abertura")
        void deveRetornarFalseQuandoFechamentoIgualAbertura() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(8, 0), LocalTime.of(8, 0));

            assertThat(expediente.validateHorarios()).isFalse();
        }

        @Test
        @DisplayName("deve retornar false quando o fechamento for antes da abertura")
        void deveRetornarFalseQuandoFechamentoAntesDaAbertura() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(18, 0), LocalTime.of(8, 0));

            assertThat(expediente.validateHorarios()).isFalse();
        }

        @Test
        @DisplayName("deve retornar false quando a hora de abertura for nula")
        void deveRetornarFalseQuandoAberturaNula() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", null, LocalTime.of(18, 0));

            assertThat(expediente.validateHorarios()).isFalse();
        }

        @Test
        @DisplayName("deve retornar false quando a hora de fechamento for nula")
        void deveRetornarFalseQuandoFechamentoNulo() {
            var expediente = new RestauranteExpediente(idRestaurante, "SEGUNDA", LocalTime.of(8, 0), null);

            assertThat(expediente.validateHorarios()).isFalse();
        }
    }

    @Test
    @DisplayName("construtor com id deve manter todos os campos preenchidos corretamente")
    void construtorComIdDeveManterCampos() {
        Long id = 10L;
        var expediente = new RestauranteExpediente(id, idRestaurante, "SEXTA", LocalTime.of(11, 0), LocalTime.of(23, 0));

        assertThat(expediente.getId()).isEqualTo(id);
        assertThat(expediente.getIdRestaurante()).isEqualTo(idRestaurante);
        assertThat(expediente.getDiaSemana()).isEqualTo("SEXTA");
        assertThat(expediente.getHoraAbertura()).isEqualTo(LocalTime.of(11, 0));
        assertThat(expediente.getHoraFechamento()).isEqualTo(LocalTime.of(23, 0));
    }
}
