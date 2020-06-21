package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.GrupoInputModelAssembler;
import com.murillo.algafood.api.model.output.GrupoOutputModel;
import com.murillo.algafood.domain.model.Usuario;
import com.murillo.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoInputModelAssembler grupoModelAssembler;

    @GetMapping
    public List<GrupoOutputModel> listar(@PathVariable Long usuarioId) {

        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return grupoModelAssembler.toOutputModelCollection(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.removerGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.adicionarGrupo(usuarioId, grupoId);
    }
}
