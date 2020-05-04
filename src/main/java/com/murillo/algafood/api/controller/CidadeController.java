package com.murillo.algafood.api.controller;

import com.murillo.algafood.domain.exception.EstadoNaoEncontradoException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.repository.CidadeRepository;
import com.murillo.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
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

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable("cidadeId") Long cidadeId) {

        return cadastroCidade.buscarOuFalhar(cidadeId);
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@RequestBody @Valid Cidade cidade, @PathVariable("cidadeId") Long cidadeId) {

        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            return cadastroCidade.salvar(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {

        try {
            return cadastroCidade.salvar(cidade);
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
