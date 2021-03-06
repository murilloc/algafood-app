package com.murillo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomJpaRepository<T,ID> extends JpaRepository<T, ID> {

    Optional<T> findFirst();
    Optional<T> findLast();

    void detach(T entity);

}
