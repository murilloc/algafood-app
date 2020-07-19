package com.murillo.algafood.infra.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.murillo.algafood.core.storage.StorageProperties;
import com.murillo.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;


    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void remover(String nomeArquivo) {

        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket()
                    ,caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageExceotion("Não foi possível deletar o arquivo na Amazon S3");
        }

    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getContentType());
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket()
                    , caminhoArquivo
                    , novaFoto.getInputStream()
                    , objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageExceotion(" N~ao foi poss'iven enviar arquivo para Amazon S3");
        }

    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
