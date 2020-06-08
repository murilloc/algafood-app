package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.CozinhaOutputModel;
import com.murillo.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaOutputModel toOutputModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaOutputModel.class);
    }

    public List<CozinhaOutputModel> toOutputModelCollection(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }
}
