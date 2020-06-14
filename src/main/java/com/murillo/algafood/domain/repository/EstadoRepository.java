package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {


}
