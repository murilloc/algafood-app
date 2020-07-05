package com.murillo.algafood.api.model.output;

import com.murillo.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Setter
public class PedidoResumoOutputModel {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoOutputModel restaurante;
    private UsuarioOutputModel cliente;


}
