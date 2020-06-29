package com.murillo.algafood.domain.model;

import com.murillo.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Pedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido = StatusPedido.CRIADO;

    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido = new ArrayList<>();


    public void calculaValorTotal() {

        getItensPedido().forEach(ItemPedido::calculaPrecoTotal);

        this.subtotal = getItensPedido().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {

        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void associarPedidosAosItens() {

        getItensPedido().forEach(item -> item.setPedido(this));
    }

    public void confirmar() {
        setStatusPedido(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatusPedido(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    public void entregar() {
        setStatusPedido(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    private void setStatusPedido(StatusPedido novoStatusPedido) {

        if (getStatusPedido().naoPodeTransitarPara(novoStatusPedido)) {
            throw new NegocioException(
                    String.format("Status do pedido de código %s não pode ser alterado de %s para %s", getCodigo(),
                            getStatusPedido().getDescricao(),
                            novoStatusPedido.getDescricao()));
        }

        this.statusPedido = novoStatusPedido;
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }


}
