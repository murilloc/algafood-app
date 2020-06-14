package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

}
