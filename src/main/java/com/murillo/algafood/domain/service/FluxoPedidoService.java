package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;


    @Transactional
    public void confirmar(Long pedidoId) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(Long pedidoId) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.entregar();
    }

}
