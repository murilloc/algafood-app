package com.murillo.algafood.api.controller;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping
    public ResponseEntity<?> listar() {

        List<Cozinha> cozinhas = cozinhaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cozinhas);
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable Long cozinhaId) {

        return cadastroCozinha.buscarOuFalhar(cozinhaId);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {

        return cadastroCozinha.salvar(cozinha);
    }


    @PutMapping(value = "/{cozinhaId}")
    public Cozinha atualizar(@PathVariable Long cozinhaId, @Valid @RequestBody Cozinha cozinha) {

        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cadastroCozinha.salvar(cozinhaAtual);

    }

    @DeleteMapping(value = "/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long cozinhaId) {

        cadastroCozinha.excluir(cozinhaId);
    }

}
