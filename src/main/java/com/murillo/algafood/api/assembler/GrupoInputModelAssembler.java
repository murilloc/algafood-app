package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.GrupoOutputModel;
import com.murillo.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoOutputModel toOutputModel(Grupo grupo) {

        return modelMapper.map(grupo, GrupoOutputModel.class);
    }


    public List<GrupoOutputModel> toOutputModelCollection(List<Grupo> grupos) {

        return grupos.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }


}
