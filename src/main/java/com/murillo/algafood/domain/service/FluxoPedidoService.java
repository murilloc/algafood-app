package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private EnvioEmailService envioEmailService;


    @Transactional
    public void confirmar(String codigo) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - " + "Pedido confirmado")
                .corpo("pedido-confirmado.ftl")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

    @Transactional
    public void cancelar(String codigo) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigo) {

        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.entregar();
    }

}
