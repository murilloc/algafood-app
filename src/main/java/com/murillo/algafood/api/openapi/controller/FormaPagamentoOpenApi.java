package com.murillo.algafood.api.openapi.controller;

import com.murillo.algafood.api.exceptionhandler.Problem;
import com.murillo.algafood.api.model.input.FormaPagamentoInputModel;
import com.murillo.algafood.api.model.output.FormaPagamentoOutputModel;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoOpenApi {


    @ApiOperation("Busca forma de pagamento por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoOutputModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request);


    @ApiOperation(("Lista as formas de pagamento"))
    ResponseEntity<List<FormaPagamentoOutputModel>> listar(ServletWebRequest request);


    @ApiOperation("Adiciona uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada com sucesso")
    })
    FormaPagamentoOutputModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento")
                    FormaPagamentoInputModel formaPagamentoInput);


    @ApiOperation("Apaga uma forma de pagamento pelo Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída com sucesso"),
            @ApiResponse(code = 404, message = "Forma de pagameto não encontrada", response = Problem.class)
    })
    void excluir(@PathVariable Long formaPagamentoId);

}
