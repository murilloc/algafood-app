package com.murillo.algafood.controller;

import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.EntidadeNapEncontradaException;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping()
    private List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }


    //@ResponseStatus(HttpStatus.CREATED)  Forma de indicar o status da resposta por aotação
    @GetMapping("/{cozinhaId}")
    private ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {

//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas/1");
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .headers(headers).build();

        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
//      return ResponseEntity.status(HttpStatus.OK).body(cozinha);
//        return ResponseEntity.ok(cozinha); // simplificação da linha acima

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }

        //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.notFound().build();

    }

// O recurso também pode ser mapeado desta forma
//    @GetMapping("/{cozinhaId}")
//    private Cozinha buscar(@PathVariable("cozinhaId") Long id){
//        return cozinhaRepository.buscar(id);
//    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {

        return cozinhaRepository.salvar(cozinha);
    }


    @PutMapping(value = "/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {

        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
        if (cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
            return ResponseEntity.ok(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping(value = "/{cozinhaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long cozinhaId) {
        try {
            cadastroCozinha.excluir(cozinhaId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNapEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            // Http Status 400 poderia ser aplicado mas é muito abrangent.e CONFLICT(409) é mais específico
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
