package com.murillo.algafood.infra.spec;


import com.murillo.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class RestauranteFreteGratisSpecs implements Specification<Restaurante> {

    private static final long serialVersionUID = 1L;

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }
}
