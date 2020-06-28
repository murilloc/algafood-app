package com.murillo.algafood.core.modelmapper;

import com.murillo.algafood.api.model.input.ItemPedidoInputModel;
import com.murillo.algafood.api.model.output.EnderecoOutputModel;
import com.murillo.algafood.domain.model.Endereco;
import com.murillo.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInputModel.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoOutputModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoOutputModel.class);

        enderecoToEnderecoOutputModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest,value) -> enderecoModelDest.getCidade().setEstado(value));

        return modelMapper;
    }
}
