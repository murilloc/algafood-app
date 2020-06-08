package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.RestauranteOutputModel;
import com.murillo.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteInputModelAssembler {

    private final ModelMapper modelMapper;

    public RestauranteInputModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public RestauranteOutputModel toOutputModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteOutputModel.class);
    }


    public List<RestauranteOutputModel> toModelCollection(List<Restaurante> restaurantes) {

        return restaurantes.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }


}
