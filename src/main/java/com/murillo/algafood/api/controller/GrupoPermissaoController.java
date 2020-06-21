package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.PermissaoInputModelAssembler;
import com.murillo.algafood.api.model.output.PermissaoOutputModel;
import com.murillo.algafood.domain.model.Grupo;
import com.murillo.algafood.domain.model.Permissao;
import com.murillo.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private PermissaoInputModelAssembler permissaoModelAssembler;

    @Autowired
    private CadastroGrupoService cadastroGrupo;


    @GetMapping
    public List<PermissaoOutputModel> listarPermissoes(@PathVariable Long grupoId) {

        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        List<Permissao> permissoes = grupo.getPermissoes();

        return permissaoModelAssembler.toOutputModelCollection(permissoes);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);

    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {

        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);

    }
}
