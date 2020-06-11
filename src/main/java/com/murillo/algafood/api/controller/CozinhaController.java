package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.CozinhaInputModelAssembler;
import com.murillo.algafood.api.assembler.CozinhaInputModelDisassembler;
import com.murillo.algafood.api.model.input.CozinhaInputModel;
import com.murillo.algafood.api.model.output.CozinhaOutputModel;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaInputModelDisassembler cozinhaInputModelDisassembler;

    @Autowired
    private CozinhaInputModelAssembler cozinhaInputModelAssembler;


    @GetMapping
    public List<CozinhaOutputModel> listar() {

        List<Cozinha> cozinhas = cozinhaRepository.findAll();
        return cozinhaInputModelAssembler.toOutputModelCollection(cozinhas);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaOutputModel buscar(@PathVariable Long cozinhaId) {

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        return cozinhaInputModelAssembler.toOutputModel(cozinha);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutputModel adicionar(@RequestBody @Valid CozinhaInputModel cozinhaInput) {

        Cozinha novaCozinha = cozinhaInputModelDisassembler.toDomainObject(cozinhaInput);
        novaCozinha = cadastroCozinha.salvar(novaCozinha);
        CozinhaOutputModel cozinhaOutputModel =cozinhaInputModelAssembler.toOutputModel(novaCozinha);
        return cozinhaOutputModel;
    }


    @PutMapping(value = "/{cozinhaId}")
    public CozinhaOutputModel atualizar(@PathVariable Long cozinhaId,
                                        @Valid @RequestBody CozinhaInputModel cozinhaInput) {

        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
        cozinhaInputModelDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaInputModelAssembler.toOutputModel(cozinhaAtual);

    }

    @DeleteMapping(value = "/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long cozinhaId) {

        cadastroCozinha.excluir(cozinhaId);
    }

}
