package com.murillo.algafood;

import com.murillo.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CadastroCozinhaIT {

    @Autowired
    CadastroCozinhaService cadastroCozinha;


    @Test
    public void deveAtribuirCozinhaId_QuandoCadastrarCozinhaComDadosCorretos() {

        //Cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");


        //Ação
        cadastroCozinha.salvar(novaCozinha);

        //Validação - Happy path
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
        assertThat(novaCozinha.getNome()).isEqualTo("Chinesa");

    }

    @Test
    public void deveFalharAoCadastrarCozinha_QuandoCozinhaSemNome() {

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome("");
            novaCozinha = cadastroCozinha.salvar(novaCozinha);
        });

    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
            cadastroCozinha.excluir(1L);
        });

    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        Assertions.assertThrows(CozinhaNaoEncontradoException.class, () ->{
            cadastroCozinha.excluir(10L);
        });
    }

}
