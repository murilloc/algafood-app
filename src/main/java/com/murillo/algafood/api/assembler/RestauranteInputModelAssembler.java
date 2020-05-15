package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.RestauranteOutputModel;
import com.murillo.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public RestauranteOutputModel toOutputModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteOutputModel.class);
    }


    public List<RestauranteOutputModel> toModelCollection(List<Restaurante> restaurantes) {

        return restaurantes.stream()
                .map(restaurante -> toOutputModel(restaurante))
                .collect(Collectors.toList());


    }
}
