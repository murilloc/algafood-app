package com.murillo.algafood.api.controller;


import com.murillo.algafood.api.assembler.GrupoInputModelAssembler;
import com.murillo.algafood.api.assembler.GrupoInputModelDisassembler;
import com.murillo.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.murillo.algafood.api.model.input.GrupoInputModel;
import com.murillo.algafood.api.model.output.GrupoOutputModel;
import com.murillo.algafood.domain.model.Grupo;
import com.murillo.algafood.domain.repository.GrupoRepository;
import com.murillo.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoInputModelDisassembler grupoInputModelDisassembler;

    @Autowired
    private GrupoInputModelAssembler grupoInputModelAssembler;



    @GetMapping("/{grupoId}")
    public GrupoOutputModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        return grupoInputModelAssembler.toOutputModel(grupo);
    }


    @GetMapping()
    public List<GrupoOutputModel> listar() {
        List<Grupo> grupos = grupoRepository.findAll();

        return grupoInputModelAssembler.toOutputModelCollection(grupos);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoOutputModel adicionar(@RequestBody @Valid GrupoInputModel grupoInput) {

        Grupo novoGrupo = grupoInputModelDisassembler.toDomainObject(grupoInput);
        novoGrupo = cadastroGrupo.salvar(novoGrupo);
        return grupoInputModelAssembler.toOutputModel(novoGrupo);

    }


    @PutMapping("/{grupoId}")
    public GrupoOutputModel atualizar(@RequestBody @Valid GrupoInputModel grupoInput, @PathVariable() Long grupoId) {

        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
        grupoInputModelDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        grupoAtual = cadastroGrupo.salvar(grupoAtual);

        return grupoInputModelAssembler.toOutputModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {

        cadastroGrupo.excluir(grupoId);
    }




}
