package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.TipoUsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        seedTipoUsuario();
    }

    private void seedTipoUsuario() {
        List<String> tipos = List.of("DONO DE RESTAURANTE", "CLIENTE");

        tipos.forEach(nome -> {
            if (tipoUsuarioRepository.findByNome(nome).isEmpty()) {
                tipoUsuarioRepository.save(
                        TipoUsuarioEntity.builder()
                                .nome(nome)
                                .build()
                );
            }
        });
    }
}
