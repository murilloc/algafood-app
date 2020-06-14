package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.UsuarioInputModelAssembler;
import com.murillo.algafood.api.assembler.UsuarioInputModelDisassembler;
import com.murillo.algafood.api.model.input.SenhaInputModel;
import com.murillo.algafood.api.model.input.UsuarioComSenhaInputModel;
import com.murillo.algafood.api.model.input.UsuarioInputModel;
import com.murillo.algafood.api.model.output.UsuarioOutputModel;
import com.murillo.algafood.domain.model.Usuario;
import com.murillo.algafood.domain.repository.UsuarioRepository;
import com.murillo.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioInputModelAssembler usuarioInputModelAssembler;

    @Autowired
    private UsuarioInputModelDisassembler usuarioInputModelDisassembler;


    @GetMapping()
    public List<UsuarioOutputModel> listar() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarioInputModelAssembler.toOutputModelCollection(usuarios);
    }

    @GetMapping(value = "/{usuarioId}")
    public UsuarioOutputModel buscar(@PathVariable Long usuarioId) {

        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
        return usuarioInputModelAssembler.toOutputModel(usuario);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioOutputModel adicionar(@RequestBody @Valid UsuarioComSenhaInputModel usuarioInput) {

        Usuario novoUsuario = usuarioInputModelDisassembler.toDomainObject(usuarioInput);
        novoUsuario = cadastroUsuario.salvar(novoUsuario);
        return usuarioInputModelAssembler.toOutputModel(novoUsuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioOutputModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputModel usuarioInput) {

        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
        usuarioInputModelDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
        return usuarioInputModelAssembler.toOutputModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody SenhaInputModel senha) {

        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}