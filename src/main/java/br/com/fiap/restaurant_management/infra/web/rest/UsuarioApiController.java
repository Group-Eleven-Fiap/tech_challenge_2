package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.UsuarioController;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.dto.UsuarioInput;
import br.com.fiap.restaurant_management.infra.web.dto.UsuarioTipoUsuarioInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/usuario")
@RequiredArgsConstructor
public class UsuarioApiController {

    private final UsuarioController controller;

    @PostMapping
    public Long criar(@Valid @RequestBody UsuarioInput input) {
        return controller.criar(mapToDto(input));
    }

    @PutMapping("{id}")
    public void atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioInput input) {
        controller.atualizar(id, mapToDto(input));
    }

    @PatchMapping("{usuarioId}")
    public void atualizar(@Valid @RequestBody UsuarioTipoUsuarioInput input) {
        controller.atualizarUsuarioTipoUsuario(input.getUsuarioId(), input.getTipoUsuarioId());
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable Long id) {
        controller.excluir(id);
    }

    @GetMapping
    public List<UsuarioOutputDto> consultar() {
        return controller.consultar();
    }

    private UsuarioInputDto mapToDto(UsuarioInput input) {
        return new UsuarioInputDto(
                input.getNome(),
                input.getEmail(),
                input.getLogin(),
                input.getSenha(),
                input.getTipoUsuarioId());
    }
}
