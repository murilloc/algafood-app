package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.EstadoIdInputModel;
import com.murillo.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoIdInputModel estadoInputModel) {

        return modelMapper.map(estadoInputModel, Estado.class);
    }

    public void copyToDomainObject(EstadoIdInputModel estadoInputModel, Estado estado) {
        modelMapper.map(estadoInputModel, estado);
    }

}
