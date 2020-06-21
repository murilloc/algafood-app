package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.UsuarioInputModelAssembler;
import com.murillo.algafood.api.model.output.UsuarioOutputModel;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class UsuarioResponsavelRestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioInputModelAssembler usuarioInputModelAssembler;

    @GetMapping
    public List<UsuarioOutputModel> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        return usuarioInputModelAssembler.toOutputModelCollection(restaurante.getUsuarios());

    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void adicionarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.adicionarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removerResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.removerResponsavel(restauranteId, usuarioId);
    }

}
