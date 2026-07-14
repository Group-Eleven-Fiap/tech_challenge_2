package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.TipoUsuarioController;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.config.TipoUsuarioControllerApi;
import br.com.fiap.restaurant_management.infra.web.dto.TipoUsuarioInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/tipo-usuario")
@RequiredArgsConstructor
public class TipoUsuarioApiController implements TipoUsuarioControllerApi {

    private final TipoUsuarioController controller;

    @Override
    public Long criar(@Valid @RequestBody TipoUsuarioInput input) {
        return controller.criar(mapToDto(input));
    }

    @Override
    public void atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioInput input) {
        controller.atualizar(id, mapToDto(input));
    }

    @Override
    public void excluir(@PathVariable Long id) {
        controller.excluir(id);
    }

    @Override
    public List<TipoUsuarioOutputDto> consultar() {
        return controller.consultar();
    }

    private TipoUsuarioInputDto mapToDto(TipoUsuarioInput input) {
        return new TipoUsuarioInputDto(input.getNome());
    }
}
