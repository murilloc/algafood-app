package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long> , CustomRestauranteRepository, JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
    List<Restaurante> findAll();

    List<Restaurante> findByTaxaFreteIsBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> streamByNomeContainingAndCozinhaId(String nome, Long cozinha);

    @Query("select r from Restaurante r where r.nome like %:nome% and r.cozinha.id = :id")
    List<Restaurante> consultarPorNomeECozinhaId(String nome, @Param("id") Long cozinha);

    @Query(value = "from Restaurante r where r.nome like %:nome%")
    List<Restaurante> consultaPorNome(String nome);

    List<Restaurante> consultarPorNomeCozinha(@Param("nome") String nome);

    Optional<Restaurante> findFirstByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContainingOrderByNome(String nome);

    Long countByCozinhaId(Long cozinhaId);




}
