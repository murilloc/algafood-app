package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.FormaPagamentoInputModelAssembler;
import com.murillo.algafood.api.model.output.FormaPagamentoOutputModel;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    CadastroRestauranteService cadastroRestaurante;

    @Autowired
    FormaPagamentoInputModelAssembler formaPagamentoInputModelAssembler;

    @GetMapping
    public List<FormaPagamentoOutputModel> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        return formaPagamentoInputModelAssembler.toOutputModelCollection(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {

        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {

        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

    }


}
