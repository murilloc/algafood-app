package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.CozinhaInputModelAssembler;
import com.murillo.algafood.api.assembler.CozinhaInputModelDisassembler;
import com.murillo.algafood.api.model.input.CozinhaInputModel;
import com.murillo.algafood.api.model.output.CozinhaOutputModel;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<CozinhaOutputModel> listar(@PageableDefault(size = 5) Pageable pageable) {

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaOutputModel> cozinhasModel =  cozinhaInputModelAssembler
                .toOutputModelCollection(cozinhasPage.getContent());

        Page<CozinhaOutputModel> cozinhasOutputModelPage = new PageImpl<>(cozinhasModel,pageable, cozinhasPage.getTotalElements());
        return cozinhasOutputModelPage;
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
