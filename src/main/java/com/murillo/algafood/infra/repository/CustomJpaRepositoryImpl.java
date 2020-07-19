package com.murillo.algafood.infra.repository;

import com.murillo.algafood.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T,ID> extends SimpleJpaRepository<T,ID> implements CustomJpaRepository<T,ID> {

    private final EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findFirst() {
        var jpql = "from " + getDomainClass().getName() ;

        T entity = entityManager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<T> findLast() {
        return Optional.empty();
    }

    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }
}
