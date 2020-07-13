//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//        List<RestauranteOutputModel> restaurantesOutputModel = restauranteAssembler.toModelCollection(restaurantes);
//
//        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesOutputModel);
//
//        if ("apenas-nome".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//        } else if ("completo".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(null);
//        } else {
//
//            restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//        }
//
//        return restaurantesWrapper;
//
//    }

package com.murillo.algafood.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.murillo.algafood.api.assembler.RestauranteInputModelAssembler;
import com.murillo.algafood.api.assembler.RestauranteInputModelDisassembler;
import com.murillo.algafood.api.model.input.CozinhaIdInputModel;
import com.murillo.algafood.api.model.input.RestauranteInputModel;
import com.murillo.algafood.api.model.output.RestauranteOutputModel;
import com.murillo.algafood.api.model.view.RestauranteView;
import com.murillo.algafood.core.validation.ValidacaoException;
import com.murillo.algafood.domain.exception.CidadeNaoEncontradoException;
import com.murillo.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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
    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteInputModelAssembler restauranteAssembler;

    @Autowired
    private RestauranteInputModelDisassembler restauranteDisassembler;

    @JsonView(RestauranteView.Resumo.class)
    @GetMapping()
    public List<RestauranteOutputModel> listar() {

        return restauranteAssembler.toModelCollection(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteOutputModel> listarApenasNomes() {

        return listar();
    }


    @GetMapping(value = "/{restauranteId}")
    public RestauranteOutputModel buscar(@PathVariable("restauranteId") Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return restauranteAssembler.toOutputModel(restaurante);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutputModel adicionar(@RequestBody @Valid/* @Validated(Groups.CadastroRestaurante.class)*/ RestauranteInputModel restauranteInput) {

        try {
            return restauranteAssembler.toOutputModel(cadastroRestaurante.salvar(restauranteDisassembler.fromInputModel(restauranteInput)));
        } catch (CozinhaNaoEncontradoException | CidadeNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @Deprecated
    @PatchMapping("/{restauranteId}")
    public RestauranteOutputModel atualizarParcialmente(@RequestBody Map<String, Object> campos, @PathVariable Long restauranteId, HttpServletRequest request) {

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");

        RestauranteInputModel restauranteInput = new RestauranteInputModel();
        CozinhaIdInputModel cozinhaIdInputModel = new CozinhaIdInputModel();
        cozinhaIdInputModel.setId(restauranteAtual.getCozinha().getId());

        restauranteInput.setNome(restauranteAtual.getNome());
        restauranteInput.setTaxaFrete(restauranteAtual.getTaxaFrete());


        restauranteInput.getCozinha().setId(restauranteAtual.getCozinha().getId());


        return atualizar(restauranteInput, restauranteId);
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }

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
    public RestauranteOutputModel atualizar(@RequestBody @Valid RestauranteInputModel restauranteInput, @PathVariable("restauranteId") Long restauranteId) {

        try {
//            Restaurante restaurante = restauranteDisassembler.fromInputModel(restauranteInput);
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
            restauranteDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
//            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");

            return restauranteAssembler.toOutputModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradoException | CidadeNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    // Usado PUT porque ele  é idempotente
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {

        cadastroRestaurante.ativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void abrir(@PathVariable Long restauranteId) {

        cadastroRestaurante.abrirRestaurante(restauranteId);

    }


    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void fechar(@PathVariable Long restauranteId) {

        cadastroRestaurante.fecharRestaurante(restauranteId);

    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {

        cadastroRestaurante.inativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {

        try {
            cadastroRestaurante.ativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {

        try {
            cadastroRestaurante.inativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }


}
