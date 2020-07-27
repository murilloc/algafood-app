package com.murillo.algafood.core.email;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private ImplementacaoEmail impl = ImplementacaoEmail.FAKE;

    @NotNull
    private String remetente;

    public enum ImplementacaoEmail {
        FAKE, SENDGRID, SANDBOX
    }

    private Sandbox sandbox = new Sandbox();


    @Getter
    @Setter
    public class Sandbox {
        private String destinatario;
    }


}
