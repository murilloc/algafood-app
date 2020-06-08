package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.CidadeInputModelAssembler;
import com.murillo.algafood.api.assembler.CidadeInputModelDisassembler;
import com.murillo.algafood.api.model.input.CidadeInputModel;
import com.murillo.algafood.api.model.output.CidadeOutputModel;
import com.murillo.algafood.domain.exception.EstadoNaoEncontradoException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.repository.CidadeRepository;
import com.murillo.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidade;
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeInputModelAssembler cidadeInputModelAssembler;

    @Autowired
    private CidadeInputModelDisassembler cidadeInputModelDisassembler;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public CidadeOutputModel buscar(@PathVariable("cidadeId") Long cidadeId) {

        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        return cidadeInputModelAssembler.toOutputModel(cidade);
    }

    @PutMapping("/{cidadeId}")
    public CidadeOutputModel atualizar(@RequestBody @Valid CidadeInputModel cidadeInputModel, @PathVariable("cidadeId") Long cidadeId) {

        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
            cidadeInputModelDisassembler.copyToDomainObject(cidadeInputModel, cidadeAtual);
            cidadeAtual = cadastroCidade.salvar(cidadeAtual);
            return cidadeInputModelAssembler.toOutputModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutputModel adicionar(@RequestBody @Valid CidadeInputModel cidadeInputModel) {

        try {
            Cidade cidade = cidadeInputModelDisassembler.toDomainObject(cidadeInputModel);
            cidade = cadastroCidade.salvar(cidade);
            return cidadeInputModelAssembler.toOutputModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("cidadeId") Long cidadeId) {

        cadastroCidade.excluir(cidadeId);
    }

}
