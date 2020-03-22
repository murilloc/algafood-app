package com.murillo.algafood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.murillo.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

        return restauranteRepository.findAll();
    }

    @GetMapping(value = "/{restauranteId}")
    public Restaurante buscar(@PathVariable("restauranteId") Long restauranteId) {
        return cadastroRestaurante.buscarOuFalhar(restauranteId);
    }


    @PostMapping
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {

        try {
            return cadastroRestaurante.salvar(restaurante);
        } catch (CozinhaNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcialmente(@RequestBody Map<String, Object> campos, @PathVariable Long restauranteId, HttpServletRequest request) {

        Restaurante restauranteCadastrado = cadastroRestaurante.buscarOuFalhar(restauranteId);
        merge(campos, restauranteCadastrado, request);
        return atualizar(restauranteCadastrado, restauranteId);
    }

    private void merge(@RequestBody Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            //Converte os valores
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
            dadosOrigem.forEach((nomePropriedade, valorPropiedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@RequestBody Restaurante restaurante, @PathVariable("restauranteId") Long restauranteId) {

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");
        try {
            return cadastroRestaurante.salvar(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

}