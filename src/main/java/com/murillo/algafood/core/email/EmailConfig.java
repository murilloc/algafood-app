package com.murillo.algafood.core.email;

import com.murillo.algafood.domain.service.EnvioEmailService;
import com.murillo.algafood.infra.service.email.FakeEnvioEmailService;
import com.murillo.algafood.infra.service.email.SandboxEnvioEmailService;
import com.murillo.algafood.infra.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {

        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SENDGRID:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }
}
