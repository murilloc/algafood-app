package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.FormaPagamentoInputModelAssembler;
import com.murillo.algafood.api.assembler.FormaPagamentoInputModelDisassembler;
import com.murillo.algafood.api.model.input.FormaPagamentoInputModel;
import com.murillo.algafood.api.model.output.FormaPagamentoOutputModel;
import com.murillo.algafood.domain.model.FormaPagamento;
import com.murillo.algafood.domain.repository.FormaPagamentoRepository;
import com.murillo.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/formasPagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoInputModelAssembler formaPagamentoInputModelAssembler;

    @Autowired
    private FormaPagamentoInputModelDisassembler formaPagamentoInputModelDisassembler;

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoOutputModel buscar(@PathVariable Long formaPagamentoId) {

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        return formaPagamentoInputModelAssembler.toOutputModel(formaPagamento);

    }

    @GetMapping
    public List<FormaPagamentoOutputModel> listar() {

        List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
        return formaPagamentoInputModelAssembler.toOutputModelCollection(formasPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutputModel adicionar(@RequestBody @Valid FormaPagamentoInputModel formaPagamentoInput) {

        FormaPagamento novaFormaPagamento = formaPagamentoInputModelDisassembler.toDomainObject(formaPagamentoInput);
        novaFormaPagamento = cadastroFormaPagamento.salvar(novaFormaPagamento);
        FormaPagamentoOutputModel formaPagamentoOutputModel = formaPagamentoInputModelAssembler.toOutputModel(novaFormaPagamento);

        return formaPagamentoOutputModel;
    }

    @PutMapping(value = "/{formaPagamentoId}")
    public FormaPagamentoOutputModel atualizar(@PathVariable Long formaPagamentoId,
                                               @Valid @RequestBody FormaPagamentoInputModel formaPagamentoInput) {

        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        formaPagamentoInputModelDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);

        return formaPagamentoInputModelAssembler.toOutputModel(formaPagamentoAtual);
    }


    @DeleteMapping(value = "/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {

        cadastroFormaPagamento.excluir(formaPagamentoId);
    }


}
