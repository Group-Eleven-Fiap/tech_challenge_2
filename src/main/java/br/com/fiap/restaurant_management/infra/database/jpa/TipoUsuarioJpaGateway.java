package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.TipoUsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.TipoUsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TipoUsuarioJpaGateway implements TipoUsuarioGateway {

    private final TipoUsuarioEntityMapper mapper;
    private final TipoUsuarioRepository repository;


    @Override
    public Long criar(TipoUsuario tipoUsuario) {
        return repository.save(mapper.toEntity(tipoUsuario)).getId();
    }

    @Override
    public void atualizar(TipoUsuario tipoUsuario) {
        repository.save(mapper.toEntity(tipoUsuario));
    }

    @Override
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TipoUsuario> consultar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<TipoUsuario> consultarPorId(Long id) {
        var optionalEntity = repository.findById(id);

        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        return optionalEntity.map(mapper::toDomain);
    }
}
