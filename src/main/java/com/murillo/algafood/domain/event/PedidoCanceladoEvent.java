package com.murillo.algafood.domain.event;

import com.murillo.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class PedidoCanceladoEvent {

    private Pedido pedido;
}
