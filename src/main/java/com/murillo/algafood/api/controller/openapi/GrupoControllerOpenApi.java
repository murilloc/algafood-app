package com.murillo.algafood.api.controller.openapi;

import com.murillo.algafood.api.exceptionhandler.Problem;
import com.murillo.algafood.api.model.input.GrupoInputModel;
import com.murillo.algafood.api.model.output.GrupoOutputModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos de usuários")
    List<GrupoOutputModel> listar();

    @ApiOperation("Busca o grupo por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do grupo inexistente", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoOutputModel buscar(@ApiParam(value = "Id de um grupo", example = "1") Long grupoId);


    @ApiOperation("Atualiza um grupo buscando por Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoOutputModel atualizar(@ApiParam(name = "corpo",
            value = "Representação de um grupo com os novos dados") GrupoInputModel grupoInput,
                               @ApiParam(name = "Id de um grupo", example = "1") Long grupoId);


    @ApiOperation("Adiciona um novo grupo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo cadastrado com sucesso")
    })
    GrupoOutputModel adicionar(@ApiParam(name = "corpo",
            value = "Representação de um grupo com os novos dados") GrupoInputModel grupoInput);

    @ApiOperation("Remove um grupo buscando  pelo Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

}
