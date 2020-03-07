package com.murillo.algafood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;


    @GetMapping()
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping(value = "/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        if (restaurante.isPresent()) {
            return ResponseEntity.ok().body(restaurante.get());
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

        Optional<Restaurante> restauranteCadastrado = restauranteRepository.findById(restauranteId);
        if (restauranteCadastrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteCadastrado.get());

        return atualizar(restauranteCadastrado.get(), restauranteId);
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

            BeanUtils.copyProperties(restaurante, restauranteExistente, "id", "formasPagamento", "endereco", "dataCadastro");
            restauranteExistente = cadastroRestaurante.salvar(restauranteExistente);
            return ResponseEntity.status(HttpStatus.OK).body(restauranteExistente);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
