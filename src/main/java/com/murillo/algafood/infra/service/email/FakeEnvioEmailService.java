package com.murillo.algafood.infra.service.email;

import com.murillo.algafood.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(EnvioEmailService.Mensagem mensagem) {

        String corpo = processarTemplate(mensagem);
        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
