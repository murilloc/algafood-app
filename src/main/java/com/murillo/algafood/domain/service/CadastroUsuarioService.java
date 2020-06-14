package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.murillo.algafood.domain.model.Usuario;
import com.murillo.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";

    public Usuario buscarOuFalhar(Long usuarioId) {


        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format(("Já existe um usuário cadastrado com o email %s"), usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual do usuário não coincide com a senha informada");
        }

        usuario.setSenha(novaSenha);
    }

}
