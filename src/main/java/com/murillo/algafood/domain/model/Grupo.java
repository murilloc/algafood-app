package com.murillo.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private List<Permissao> permissoes;

    public void removerPermissao(Permissao permissao) {
        List<Permissao> permissoes = getPermissoes();
        if (permissoes.contains(permissao)) {
            this.getPermissoes().remove(permissao);
        }
    }

    public void adicionarPermissao(Permissao permissao) {
        List<Permissao> permissoes = getPermissoes();
        if (!permissoes.contains(permissao)) {
            this.getPermissoes().add(permissao);
        }
    }



}
