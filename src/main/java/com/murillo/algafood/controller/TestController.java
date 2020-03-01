package com.murillo.algafood.controller;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;


    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {

        return cozinhaRepository.findAllByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/existe-por-nome")
    public Boolean existeCozinhaPorNome(@RequestParam("nome") String nome) {

        return cozinhaRepository.existsByNome(nome);
    }



    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaunrantePorNome(BigDecimal taxaInicial, BigDecimal taxaFinal) {

        return restauranteRepository.findByTaxaFreteIsBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaunrantePorNome(String nome, Long cozinhaId) {

        return restauranteRepository.streamByNomeContainingAndCozinhaId(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> primeiroRestaurantePorNome(String nome) {

        return restauranteRepository.findFirstByNomeContaining(nome);
    }


    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> primeirosRestaurantesPorNome(String nome) {

        return restauranteRepository.findTop2ByNomeContainingOrderByNome(nome);
    }

    @GetMapping("/restaurantes/total-por-cozinha")
    public Long totalPorCozinha(Long cozinhaId) {

        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

}
