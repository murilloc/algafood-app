package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.EstadoInputModelAssembler;
import com.murillo.algafood.api.assembler.EstadoInputModelDisassembler;
import com.murillo.algafood.api.model.input.EstadoIdInputModel;
import com.murillo.algafood.api.model.output.EstadoOutputModel;
import com.murillo.algafood.domain.model.Estado;
import com.murillo.algafood.domain.repository.EstadoRepository;
import com.murillo.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstado;
    @Autowired
    private EstadoInputModelAssembler estadoInputModelAssembler;
    @Autowired
    private EstadoInputModelDisassembler estadoInputModelDisassembler;

    @GetMapping()
    public List<EstadoOutputModel> listar() {

        List<Estado> estados = estadoRepository.findAll();
        return estadoInputModelAssembler.toOutputModelCollection(estados);

    }

    @GetMapping("/{estadoId}")
    public EstadoOutputModel buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

        return estadoInputModelAssembler.toOutputModel(estado);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoOutputModel adicionar(@RequestBody @Valid EstadoIdInputModel estadoInputModel) {

        Estado estado = estadoInputModelDisassembler.toDomainObject(estadoInputModel);
        estado = cadastroEstado.salvar(estado);

        return estadoInputModelAssembler.toOutputModel(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoOutputModel autalizar(@RequestBody @Valid EstadoIdInputModel estadoInputModel, @PathVariable Long estadoId) {

        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
        estadoInputModelDisassembler.copyToDomainObject(estadoInputModel, estadoAtual);
        estadoAtual = estadoRepository.save(estadoAtual);

        return estadoInputModelAssembler.toOutputModel(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {

        cadastroEstado.excluir(estadoId);
    }


}
