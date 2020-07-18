package com.murillo.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoOutputModel {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
