package com.murillo.algafood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;


    @GetMapping()
    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    @GetMapping(value = "/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = restauranteRepository.buscar(id);

        if (restaurante != null) {
            return ResponseEntity.ok().body(restaurante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {

        try {
            restaurante = cadastroRestaurante.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

        } catch (EntidadeNaoEncontradaException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcialmente(@RequestBody Map<String, Object> campos, @PathVariable Long restauranteId) {

        Restaurante restauranteCadastrado = restauranteRepository.buscar(restauranteId);

        if (restauranteCadastrado == null) {
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteCadastrado);

        return atualizar(restauranteCadastrado, restauranteId);
    }

    private void merge(@RequestBody Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {

        ObjectMapper objectMapper = new ObjectMapper();
        //Converte os valores
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
        dadosOrigem.forEach((nomePropriedade, valorPropiedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

   @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante, @PathVariable("restauranteId") Long id) {
        try {

            if (restaurante.getCozinha() == null || restaurante.getTaxaFrete() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na composição dos dados");
            }
            Restaurante restauranteExistente = cadastroRestaurante.buscar(id);
            restauranteExistente.setNome(restaurante.getNome());
            restauranteExistente.setTaxaFrete(restaurante.getTaxaFrete());
            restauranteExistente.setCozinha(restaurante.getCozinha());
            restauranteExistente = cadastroRestaurante.atualizar(restauranteExistente);
            return ResponseEntity.status(HttpStatus.OK).body(restauranteExistente);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
