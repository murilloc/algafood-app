package com.murillo.algafood.api.controller;

import com.murillo.algafood.domain.model.Estado;
import com.murillo.algafood.domain.repository.EstadoRepository;
import com.murillo.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping()
    public ResponseEntity<?> listar() {

        return ResponseEntity.status(HttpStatus.OK).body(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable Long estadoId) {

        return cadastroEstado.buscarOuFalhar(estadoId);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {

        return cadastroEstado.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado autalizar(@RequestBody Estado estado, @PathVariable Long estadoId) {

        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return cadastroEstado.salvar(estadoAtual);
    }


    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {

        cadastroEstado.excluir(estadoId);
    }


}