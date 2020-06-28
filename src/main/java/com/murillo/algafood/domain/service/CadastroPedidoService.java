package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.exception.PedidoNaoEncontradoException;
import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

}
