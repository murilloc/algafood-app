package com.murillo.algafood.infra.spec;

import com.murillo.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class RestauranteComNomeSenhlhanteSpecs implements Specification<Restaurante> {

    private String nome;

    public RestauranteComNomeSenhlhanteSpecs(String nome) {
        this.nome = nome;
    }

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    }
}
