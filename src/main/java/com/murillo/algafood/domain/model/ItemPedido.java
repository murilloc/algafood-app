package com.murillo.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal precoTotal;

    private String observacao;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;


}
