package com.murillo.algafood.jpa;

import com.murillo.algafood.AlgafoodJpaApplication;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodJpaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
       RestauranteRepository restaunranteRepository = applicationContext.getBean(RestauranteRepository.class);
        List<Restaurante> restaunrantes  = restaunranteRepository.listar();

        for(Restaurante restaunrante:restaunrantes){
            System.out.printf("%s - %f - %s\n",restaunrante.getNome(), restaunrante.getTaxaFrete(), restaunrante.getCozinha().getNome());
        }


    }
}
