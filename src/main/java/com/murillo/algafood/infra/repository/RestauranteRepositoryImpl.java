package com.murillo.algafood.infra.repository;


import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.CustomRestauranteRepository;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import com.murillo.algafood.infra.repository.spec.RestauranteSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements CustomRestauranteRepository {

    // Sufixo Impl é mandatório

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired @Lazy // evita referência circular
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> encontrarPorNomeETaxa(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {

//"select r from Restaurante r where r.nome like :nome and r.taxaFrete between :taxaInicial and :taxaFinal";
        var jpql = new StringBuilder();
        var parametros = new HashMap<String, Object>();
        jpql.append("select r from Restaurante r where 0 = 0 ");

        if (StringUtils.hasLength(nome)) {
            jpql.append(" and r.nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaInicial != null) {
            jpql.append(" and r.taxaFrete >= :taxaInicial");
            parametros.put("taxaInicial", taxaInicial);
        }

        if (taxaFinal != null) {
            jpql.append(" and r.taxaFrete <= :taxaFinal");
            parametros.put("taxaFinal", taxaFinal);
        }

        TypedQuery<Restaurante> typedQuery = entityManager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach(typedQuery::setParameter);

        return typedQuery.getResultList();

    }

    // Usando Criteria API
    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);  // select r from restaurante r

        var predicateList = new ArrayList<Predicate>();

        if (StringUtils.hasLength(nome)) {
            Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
            predicateList.add(nomePredicate);
        }

        if (taxaInicial != null) {
            Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial);
            predicateList.add(taxaInicialPredicate);
        }
        if (taxaFinal != null) {
            Predicate taxaFinalPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal);
            predicateList.add(taxaFinalPredicate);
        }


        criteria.where(predicateList.toArray(new Predicate[0])); // AND
        TypedQuery<Restaurante> typedQuery = entityManager.createQuery(criteria);

        return typedQuery.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(RestauranteSpec.comFreteGratis().and(RestauranteSpec.comNomeSemelhante(nome)));
    }


}
