package com.murillo.algafood.jpa;

import com.murillo.algafood.AlgafoodJpaApplication;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodJpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        Cozinha cozinha1 = new Cozinha();
        Cozinha cozinha2 = new Cozinha();
        cozinha1.setNome("Italiana");
        cozinha2.setNome("Japonesa");

        CozinhaRepository cozinhaRepository   = applicationContext.getBean(CozinhaRepository.class);
        cozinha1 = cozinhaRepository.salvar(cozinha1);
        cozinha2 = cozinhaRepository.salvar(cozinha2);

        System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
        System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());




    }
}
