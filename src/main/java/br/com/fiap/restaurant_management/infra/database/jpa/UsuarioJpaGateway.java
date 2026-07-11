package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.UsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioJpaGateway implements UsuarioGateway {

    private final UsuarioEntityMapper mapper;
    private final UsuarioRepository repository;


    @Override
    public Long criar(Usuario usuario) {
        return repository.save(mapper.toEntity(usuario)).getId();
    }

    @Override
    public void atualizar(Usuario usuario) {
        repository.save(mapper.toEntity(usuario));
    }

    @Override
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Usuario> consultar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Usuario> consultarPorId(Long id) {
        var optionalEntity = repository.findById(id);

        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        return optionalEntity.map(mapper::toDomain);
    }

    @Override
    public void alterarTipoUsuario(Long usuarioId, Long tipoUsuarioId) {

    }
}
