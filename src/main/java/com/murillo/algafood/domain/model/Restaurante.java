package com.murillo.algafood.domain.model;

import com.murillo.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nome;


    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.TRUE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;

    @ManyToMany //(fetch = FetchType.EAGER) // Raramente é necessário pois é muito oneroso para o desempenho
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany //(fetch = FetchType.EAGER) // Raramente é necessário pois é muito oneroso para o desempenho
    @JoinTable(name = "usuario_responsavel_restaurante",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> usuarios = new HashSet<>();


    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        formasPagamento.add(formaPagamento);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento) {
        formasPagamento.remove(formaPagamento);
    }

    public void adicionarUsuarioResponsavel(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void removerUsuarioResponsavel(Usuario usuario) {
        usuarios.remove(usuario);
    }

}
