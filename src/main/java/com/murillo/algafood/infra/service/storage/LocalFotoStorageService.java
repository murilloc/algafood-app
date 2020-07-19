package com.murillo.algafood.infra.service.storage;

import com.murillo.algafood.core.storage.StorageProperties;
import com.murillo.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {

        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return Files.newInputStream(arquivoPath);
        } catch (IOException e) {
            throw new StorageExceotion("Fão foi possível recuperar o arquivo!",e.getCause());
        }

    }

    @Override
    public void remover(String nomeArquivo) {

        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new StorageExceotion("Nao foi possível remover o arquivo ",e.getCause());
        }

    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new StorageExceotion("Não foi possível armazenar arquivo: " + e.getMessage());
        }

    }



    private Path getArquivoPath(String nomeArquivo) {

        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));

    }


}