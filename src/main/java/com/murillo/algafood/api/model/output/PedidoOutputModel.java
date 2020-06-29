package com.murillo.algafood.api.model.output;

import com.murillo.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoOutputModel {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataAtualizacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private RestauranteResumoOutputModel restaurante;
    private UsuarioOutputModel cliente;
    private FormaPagamentoOutputModel formaPagamento;
    private EnderecoOutputModel enderecoEntrega;
    private List<ItemPedidoOutputModel> itens;


}
