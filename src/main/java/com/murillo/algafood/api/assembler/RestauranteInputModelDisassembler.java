package com.murillo.algafood.api.assembler;


import com.murillo.algafood.api.model.input.RestauranteInputModel;
import com.murillo.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputModelDisassembler {


    @Autowired
    ModelMapper modelMapper;


    public Restaurante fromInputModel(RestauranteInputModel restauranteInputModel) {

        return modelMapper.map(restauranteInputModel, Restaurante.class);

    }
}
