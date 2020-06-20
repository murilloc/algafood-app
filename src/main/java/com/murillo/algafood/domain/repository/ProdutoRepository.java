package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Produto;
import com.murillo.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("produto") Long produtoId, @Param("restaurante") Long restauranteId);

    List<Produto> findByRestaurante(Restaurante restaurante);

    List<Produto> findByRestauranteId(Long restauranteId);

    void deleteById(Long produtoId);
}
