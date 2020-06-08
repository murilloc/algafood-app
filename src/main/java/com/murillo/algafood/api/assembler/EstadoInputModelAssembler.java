package com.murillo.algafood.api.assembler;


import com.murillo.algafood.api.model.output.EstadoOutputModel;
import com.murillo.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoOutputModel toOutputModel(Estado estado) {
        return modelMapper.map(estado, EstadoOutputModel.class);
    }


    public List<EstadoOutputModel> toOutputModelCollection(List<Estado> estados) {

        return estados.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }
}
