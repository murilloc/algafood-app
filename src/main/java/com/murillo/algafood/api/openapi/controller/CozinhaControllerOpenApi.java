package com.murillo.algafood.api.openapi.controller;

import com.murillo.algafood.api.exceptionhandler.Problem;
import com.murillo.algafood.api.model.input.CozinhaInputModel;
import com.murillo.algafood.api.model.output.CozinhaOutputModel;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", name = "campos", paramType = "string")
    })
    @ApiOperation("Lista as cozinhas com paginação")
    Page<CozinhaOutputModel> listar(Pageable pageable);

    @ApiOperation("Busca a cozinha por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", name = "campos", paramType = "string")
    })
    CozinhaOutputModel buscar(@ApiParam(value = "Id de uma cozinha", example = "1") Long cozinhaId);

    CozinhaOutputModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha") CozinhaInputModel cozinhaInput);
}
