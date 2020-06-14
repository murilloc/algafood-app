package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {

}
