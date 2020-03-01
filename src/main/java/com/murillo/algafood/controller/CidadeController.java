package com.murillo.algafood.controller;

import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.exception.ViolacaoDeIntegridadeException;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.repository.CidadeRepository;
import com.murillo.algafood.domain.repository.EstadoRepository;
import com.murillo.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidade;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;


    @GetMapping
    public ResponseEntity<?> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cidades);
    }


    @GetMapping("/{cidadeId}")
    public ResponseEntity<?> buscar(@PathVariable("cidadeId") Long cidadeId) {

        try {
            Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
            return ResponseEntity.status(HttpStatus.OK).body(cidade.get());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@RequestBody Cidade cidade, @PathVariable("cidadeId") Long cidadeId) {
        try {
            Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);

            if (cidadeAtual.isPresent()) {
                BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");

                Cidade cidadeAtualizada = cadastroCidade.salvar(cidadeAtual.get());
                return ResponseEntity.ok(cidadeAtualizada);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {

        try {
            Cidade novaCidade = cidadeRepository.save(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaCidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ViolacaoDeIntegridadeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable("cidadeId") Long cidadeId) {

        try {
            cidadeRepository.deleteById(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
