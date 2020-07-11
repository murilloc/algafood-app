package com.murillo.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

   @Email
   @NotBlank
    private String email;

    private String senha;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private List<Grupo> grupos;


    public boolean senhaCoincideCom(String senha) {
        return getSenha().equals(senha);
    }

    public boolean senhaNaoCoincideCom(String senha) {
        return !senhaCoincideCom(senha);
    }


    public void adicionarGrupo(Grupo grupo) {
        List<Grupo> grupos = getGrupos();
        if (!grupos.contains(grupo)) grupos.add(grupo);
    }

    public void removerGrupo(Grupo grupo) {
        List<Grupo> grupos = getGrupos();
        grupos.remove(grupo);
    }
}
