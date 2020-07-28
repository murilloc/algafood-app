package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigo) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();
        pedidoRepository.save(pedido); // para disparar o evento
    }

    @Transactional
    public void cancelar(String codigo) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigo) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.entregar();
    }

}
