package com.murillo.algafood.core.modelmapper;

import com.murillo.algafood.api.model.input.EstadoIdInputModel;
import com.murillo.algafood.api.model.output.EnderecoOutputModel;
import com.murillo.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoOutputModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoOutputModel.class);

        enderecoToEnderecoOutputModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest,value) -> enderecoModelDest.getCidade().setEstado(value));

        return modelMapper;
    }
}
