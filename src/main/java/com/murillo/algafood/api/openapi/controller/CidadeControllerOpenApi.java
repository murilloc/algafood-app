package com.murillo.algafood.api.openapi.controller;

import com.murillo.algafood.api.exceptionhandler.Problem;
import com.murillo.algafood.api.model.input.CidadeInputModel;
import com.murillo.algafood.api.model.output.CidadeOutputModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    List<CidadeOutputModel> listar();

    @ApiOperation("Busca a cidade por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da Cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutputModel buscar(@ApiParam(value = "Id de uma cidade", example = "1")   Long cidadeId);

    @ApiOperation("Atualiza uma cidade por Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutputModel atualizar(@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                 CidadeInputModel cidadeInput,
                                @ApiParam(name = "Id de uma cidade", example = "1")
                                Long cidadeId);


    @ApiOperation("Adiciona uma nova cidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade cadastrada com sucesso")
    })
    CidadeOutputModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                               CidadeInputModel cidadeInputModel);


    @ApiOperation("Remove uma cidade pelo Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId);

}
