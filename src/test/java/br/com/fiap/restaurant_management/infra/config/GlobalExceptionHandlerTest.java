package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.exception.InvalidCredentialsException;
import br.com.fiap.restaurant_management.core.exception.ResourceAlreadyExistsException;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private WebRequest request;

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    // helper to read extensions/properties from ProblemDetail using reflection
    private Object getExtension(ProblemDetail pd, String key) {
        try {
            // search declared fields for a Map
            for (Field f : pd.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(pd);
                if (val instanceof Map) {
                    Map map = (Map) val;
                    if (map.containsKey(key)) return map.get(key);
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Test
    void handleResourceNotFound_withField_shouldPopulateProblemDetail() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Cardapio", "id", 1L);
        when(request.getDescription(false)).thenReturn("uri=/v2/cardapio/1");

        ProblemDetail pd = handler.handleResourceNotFound(ex, request);

        assertNotNull(pd);
        assertEquals(URI.create("https://api.restaurant-management.com/errors/resource-not-found"), pd.getType());
        assertEquals("Recurso não encontrado", pd.getTitle());
        assertEquals(ex.getMessage(), pd.getDetail());
        assertEquals("/v2/cardapio/1", String.valueOf(getExtension(pd, "path")));
        assertEquals("id", String.valueOf(getExtension(pd, "fieldName")));
        assertEquals(1L, Long.valueOf(String.valueOf(getExtension(pd, "fieldValue"))).longValue());
        assertNotNull(getExtension(pd, "timestamp"));
    }

    @Test
    void handleInvalidCredentials_shouldPopulateProblemDetail() {
        InvalidCredentialsException ex = new InvalidCredentialsException("Credenciais inválidas");
        when(request.getDescription(false)).thenReturn("uri=/login");

        ProblemDetail pd = handler.handleInvalidCredentials(ex, request);

        assertNotNull(pd);
        assertEquals(URI.create("https://api.restaurant-management.com/errors/invalid-credentials"), pd.getType());
        assertEquals("Credenciais inválidas", pd.getTitle());
        assertEquals(ex.getMessage(), pd.getDetail());
        assertEquals("/login", String.valueOf(getExtension(pd, "path")));
        assertNotNull(getExtension(pd, "timestamp"));
    }

    @Test
    void handleBusinessRuleException_shouldPopulateProblemDetailWithCode() {
        BusinessRuleException ex = new BusinessRuleException("ERR_CODE", "Regra violada");
        when(request.getDescription(false)).thenReturn("uri=/v2/cardapio");

        ProblemDetail pd = handler.handleBusinessRuleException(ex, request);

        assertNotNull(pd);
        assertEquals(URI.create("https://api.restaurant-management.com/errors/business-rule-violation"), pd.getType());
        assertEquals("Violação de regra de negócio", pd.getTitle());
        assertEquals("Regra violada", pd.getDetail());
        assertEquals("ERR_CODE", String.valueOf(getExtension(pd, "code")));
        assertEquals("/v2/cardapio", String.valueOf(getExtension(pd, "path")));
        assertNotNull(getExtension(pd, "timestamp"));
    }

    @Test
    void handleResourceAlreadyExistsException_shouldPopulateProblemDetailWithCode() {
        ResourceAlreadyExistsException ex = new ResourceAlreadyExistsException("Já existe");
        when(request.getDescription(false)).thenReturn("uri=/v2/cardapio");

        ProblemDetail pd = handler.handleResourceAlreadyExistsException(ex, request);

        assertNotNull(pd);
        assertEquals(URI.create("https://api.restaurant-management.com/errors/resource-already-exists"), pd.getType());
        assertEquals("Registro duplicado", pd.getTitle());
        assertEquals("Já existe", pd.getDetail());
        assertEquals("RESOURCE_ALREADY_EXISTS", String.valueOf(getExtension(pd, "code")));
        assertEquals("/v2/cardapio", String.valueOf(getExtension(pd, "path")));
        assertNotNull(getExtension(pd, "timestamp"));
    }

    @Test
    void handleGlobalException_shouldPopulateProblemDetailWithErrorId() {
        Exception ex = new RuntimeException("boom");
        when(request.getDescription(false)).thenReturn("uri=/any");

        ProblemDetail pd = handler.handleGlobalException(ex, request);

        assertNotNull(pd);
        assertEquals(URI.create("https://api.restaurant-management.com/errors/internal-server-error"), pd.getType());
        assertEquals("Erro interno do servidor", pd.getTitle());
        assertEquals("Um erro inesperado ocorreu no processamento da sua solicitação", pd.getDetail());
        assertEquals("/any", String.valueOf(getExtension(pd, "path")));
        assertEquals(ex.getClass().getSimpleName(), String.valueOf(getExtension(pd, "errorId")));
        assertNotNull(getExtension(pd, "timestamp"));
    }
}
