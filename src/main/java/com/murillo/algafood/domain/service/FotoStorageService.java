package com.murillo.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void remover(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }


    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }


    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }


}