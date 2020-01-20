package com.murillo.algafood.jpa;

import com.murillo.algafood.AlgafoodJpaApplication;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodJpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
        List<Cozinha> cozinhas  = cozinhaRepository.listar();

        for(Cozinha cozinha:cozinhas){
            System.out.println(cozinha.getNome());
        }


    }
}
