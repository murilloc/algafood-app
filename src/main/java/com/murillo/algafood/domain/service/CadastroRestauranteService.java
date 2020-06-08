package com.murillo.algafood.domain.service;


import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Restaurante;
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
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    CadastroCozinhaService cadastroCozinha;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {

        return restauranteRepository
                .findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
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


}
