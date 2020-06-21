package com.murillo.algafood.domain.service;


import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.exception.GrupoNaoEncontradoException;
import com.murillo.algafood.domain.model.Grupo;
import com.murillo.algafood.domain.model.Permissao;
import com.murillo.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CadastroGrupoService {

    public static final String MSG_GRUPO_EM_USO = "Não é possível excluir grupo id %d pois está sendo usado no momento";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissao;


    public List<Grupo> listar() {

        return grupoRepository.findAll();

    }


    @Transactional
    public Grupo salvar(Grupo grupo) {

        return grupoRepository.save(grupo);

    }


    @Transactional
    public void excluir(Long grupoId) {

        try {
            buscarOuFalhar(grupoId);
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch (EntidadeNaoEncontradaException e) {
            throw new GrupoNaoEncontradoException(grupoId);
        } catch (EntidadeEmUsoException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
        }

    }

    public Grupo buscarOuFalhar(Long grupoId) {
        return grupoRepository.findById(grupoId).orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }


    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {

        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);

    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {

        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
        grupo.adicionarPermissao(permissao);

    }


}
