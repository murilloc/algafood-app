package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Grupo;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long> {

}
