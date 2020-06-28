package com.murillo.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInputModel {


    @Valid
    @NotNull
    private RestauranteIdInputModel restaurante;

    @Valid
    @NotNull
    private FormaPagamentoIdInputModel formaPagamento;

    @Valid
    @NotNull
    private EnderecoInputModel enderecoEntrega;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoInputModel> itens;


}
