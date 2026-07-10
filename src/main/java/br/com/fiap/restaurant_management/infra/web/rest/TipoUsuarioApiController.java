package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.TipoUsuarioController;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.dto.TipoUsuarioInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/tipo-usuario")
@RequiredArgsConstructor
public class TipoUsuarioApiController {

    private final TipoUsuarioController controller;

    @PostMapping
    public Long criar(@Valid @RequestBody TipoUsuarioInput input) {
        return controller.criar(mapToDto(input));
    }

    @PutMapping("{id}")
    public void atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioInput input) {
        controller.atualizar(id, mapToDto(input));
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable Long id) {
        controller.excluir(id);
    }

    @GetMapping
    public List<TipoUsuarioOutputDto> consultar() {
        return controller.consultar();
    }

    private TipoUsuarioInputDto mapToDto(TipoUsuarioInput input) {
        return new TipoUsuarioInputDto(input.getNome());
    }
}
