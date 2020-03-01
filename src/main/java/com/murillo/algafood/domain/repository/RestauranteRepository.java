package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByTaxaFreteIsBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> streamByNomeContainingAndCozinhaId(String nome, Long cozinha);

    Optional<Restaurante> findFirstByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContainingOrderByNome(String nome);

    Long countByCozinhaId(Long cozinhaId);



}
