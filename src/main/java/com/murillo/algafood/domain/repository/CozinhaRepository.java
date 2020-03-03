package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findAllByNome(String nome);

    Optional<Cozinha> findByNome(String nome);

    List<Cozinha> findAllByNomeContaining(String nome);

    boolean existsByNome(String nome);



}
