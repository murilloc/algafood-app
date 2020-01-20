package com.murillo.algafood.jpa;

import com.murillo.algafood.AlgafoodJpaApplication;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class AlteracaoCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodJpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();

        cozinha1.setId(1L);
        cozinha1.setNome("Brasileira");

        cozinha1 = cozinhaRepository.salvar(cozinha1);


        System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());


    }
}
