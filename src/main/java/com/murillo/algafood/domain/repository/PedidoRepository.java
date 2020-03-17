package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
}
