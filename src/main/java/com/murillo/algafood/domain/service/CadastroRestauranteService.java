package com.murillo.algafood.domain.service;


import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.murillo.algafood.domain.model.*;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroRestauranteService {

    private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido pois está em uso";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;


    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);

        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {

        return restauranteRepository
                .findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }


    @Transactional
    public void ativar(Long restauranteId) {

        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.ativar();
        // Contexto JPA gerenciado não preciso fazer o update na tabela
    }

    @Transactional
    public void inativar(Long restauranteId) {

        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.inativar();
    }

    @Transactional
    public void fecharRestaurante(Long restauranteId) {
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.fechar();
    }

    @Transactional
    public void abrirRestaurante(Long restauranteId) {
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
        restauranteAtual.abrir();
    }

    @Transactional
    public void excluir(Long restauranteId) {
        try {
            cozinhaRepository.deleteById(restauranteId);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
        }

    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);

    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);

    }


    @Transactional
    public void adicionarResponsavel(Long restauranteId, Long usuarioId) {

        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        restaurante.adicionarUsuarioResponsavel(usuario);
    }

    @Transactional
    public void removerResponsavel(Long restauranteId, Long usuarioId) {

        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        restaurante.removerUsuarioResponsavel(usuario);
    }

}
