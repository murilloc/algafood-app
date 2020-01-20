package com.murillo.algafood.controller;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping()
    private List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }


    @GetMapping("/{cozinhaId}")
    private Cozinha biscar(@PathVariable Long cozinhaId) {
        return cozinhaRepository.buscar(cozinhaId);
    }

// O recurso também pode ser mapeado desta forma
//    @GetMapping("/{cozinhaId}")
//    private Cozinha biscar(@PathVariable("cozinhaId") Long id){
//        return cozinhaRepository.buscar(id);
//    }



}
