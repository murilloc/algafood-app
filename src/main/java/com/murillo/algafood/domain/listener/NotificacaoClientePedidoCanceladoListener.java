package com.murillo.algafood.domain.listener;

import com.murillo.algafood.domain.event.PedidoCanceladoEvent;
import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    //@EventListener
    @TransactionalEventListener //(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoCancelarPedido(PedidoCanceladoEvent event){

        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - " + "Pedido cancelado")
                .corpo("pedido-cancelado.ftl")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }
}
