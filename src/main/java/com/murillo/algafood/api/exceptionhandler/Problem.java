package com.murillo.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;
    @ApiModelProperty(example = "Https://algafood.com.br/recurso-nao-encontrado", position = 5)
    private String type;
    @ApiModelProperty(example = "Dados inválidos", position = 7)
    private String title;
    @ApiModelProperty(example = "Não existe cozinha com o código 10", position = 10)
    private String detail;
    @ApiModelProperty(example = "Não existe cozinha com o código 10", position = 15)
    private String userMessage;
    @ApiModelProperty(example = "2020-04-02T23:24:18.7554Z", value = "data/hora no formato ISO", position = 15)
    private OffsetDateTime timestamp;
    @ApiModelProperty(value = "Lista de Objetos ou campos que geraram o erro (Opcional)", position = 20)
    private List<Object> objects = new ArrayList<>();

    @ApiModel("Objeto Problema")
    @Getter
    @Setter
    @Builder
    public static class Object {
        @ApiModelProperty(example = "preço")
        private String name;
        @ApiModelProperty(example = "O preço do produto é obrigatório")
        private String userMessage;
    }

}
