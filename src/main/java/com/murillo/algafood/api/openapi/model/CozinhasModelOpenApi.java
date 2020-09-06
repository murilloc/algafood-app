package com.murillo.algafood.api.openapi.model;

import com.murillo.algafood.api.model.output.CozinhaOutputModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaOutputModel> {

}
