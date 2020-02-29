package com.murillo.algafood.domain.service;


import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
        if (cozinha == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o codigo %d", cozinhaId));
        }

        restaurante.setCozinha(cozinha);

        return restauranteRepository.salvar(restaurante);
    }

    public Restaurante atualizar(Restaurante restaurante) {

        Restaurante restauranteCadastrado = buscar(restaurante.getId());

        if (restauranteCadastrado == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não eixste restaurante dom o código %d", restaurante.getId()));
        }

        restauranteCadastrado = restaurante;
        return salvar(restauranteCadastrado);

    }


    public Restaurante buscar(Long id) {

        Restaurante restaurante = restauranteRepository.buscar(id);

        if (restaurante == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe restaurante com o código %d", id));
        }

        return restaurante;
    }


}
