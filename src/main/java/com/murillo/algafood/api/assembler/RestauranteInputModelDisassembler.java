package com.murillo.algafood.api.assembler;


import com.murillo.algafood.api.model.input.RestauranteInput;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputModelDisassembler {


    @Autowired
    ModelMapper modelMapper;


    public Restaurante fromInputModel(RestauranteInput restauranteInput) {

        return modelMapper.map(restauranteInput, Restaurante.class);

    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante){

        // Para evitar a exception
        // org.hibernate.HibernateException: identifier of an instance of com.murillo.algafood.domain.model.Cozinha was altered from 1 to 3
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() != null ){
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }
}
