package com.murillo.algafood.controller;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import com.murillo.algafood.infra.repository.spec.RestauranteComNomeSenhlhanteSpec;
import com.murillo.algafood.infra.repository.spec.RestauranteFreteGratisSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
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

    @GetMapping("/cozinhas/primeiro")
    public Optional<Cozinha> cozinhaPrimeiro() {

        return cozinhaRepository.findFirst();
    }

    @GetMapping("/cozinhas/existe-por-nome")
    public Boolean existeCozinhaPorNome(@RequestParam("nome") String nome) {

        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/restaurantes/por-nome-e-taxa-frete")
    List<Restaurante> restaurantesPorNomeEFrete(String nomeRestaurante, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.encontrarPorNomeETaxa(nomeRestaurante, taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/primeiro")
    Optional<Restaurante> restaurantesPrimeiro() {
        return restauranteRepository.findFirst();
    }

    @GetMapping("/restaurantes/por-nome-criteria")
    List<Restaurante> restaurantesPorNomeCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }


    @GetMapping("/restaurantes/frete-gratis-por-nome")
    List<Restaurante> restaurantesComFreteGratisPorNome(String nome) {
        var comFreteGratis = new RestauranteFreteGratisSpec();
        var comNomeSemelhante = new RestauranteComNomeSenhlhanteSpec(nome);

        // É necessário configurar o repositorio para trabalhar com specifications
        return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));
    }

    @GetMapping("/restaurantes/frete-gratis-factory")
    List<Restaurante> restaurantesComFreteGratisPorNomeFactory(String nome) {

        return restauranteRepository.findComFreteGratis(nome);
    }



    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantePorNome(BigDecimal taxaInicial, BigDecimal taxaFinal) {

        return restauranteRepository.findByTaxaFreteIsBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome-cozinha")
    public List<Restaurante> restaurantePorNomeCozinha(@PathParam("NomeCozinha") String nomeCozinha) {

        return restauranteRepository.consultarPorNomeCozinha(nomeCozinha);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantePorNome(String nome, Long cozinhaId) {

        return restauranteRepository.streamByNomeContainingAndCozinhaId(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/por-nome-e-cozinha")
    public List<Restaurante> restaurantePorNomeECozinhaId(String nome, Long cozinhaId) {

        return restauranteRepository.consultarPorNomeECozinhaId(nome, cozinhaId);
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
