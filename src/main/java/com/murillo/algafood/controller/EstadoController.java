package com.murillo.algafood.controller;

import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.model.Estado;
import com.murillo.algafood.domain.repository.EstadoRepository;
import com.murillo.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<?> buscar(@PathVariable Long estadoId) {

        try {
            Optional<Estado> estado = estadoRepository.findById(estadoId);
            return ResponseEntity.ok(estado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Estado estado) {
        Estado novoEstado = cadastroEstado.salvar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> autalizar(@RequestBody Estado estado, @PathVariable Long estadoId) {

        Estado estadoCadastrado = estadoRepository.findById(estadoId).orElse(null);

        if (estadoCadastrado != null) {
            BeanUtils.copyProperties(estado, estadoCadastrado, "id");
            estadoCadastrado = cadastroEstado.salvar(estadoCadastrado);
            return ResponseEntity.ok(estadoCadastrado);
        }

        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> excluir(@PathVariable Long estadoId) {
        try {
            cadastroEstado.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


}
