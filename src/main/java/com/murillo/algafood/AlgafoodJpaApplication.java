package com.murillo.algafood;

import com.murillo.algafood.infra.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) // Custo rRepo
public class AlgafoodJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgafoodJpaApplication.class, args);
    }

}
