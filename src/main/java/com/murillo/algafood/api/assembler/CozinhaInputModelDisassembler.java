package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.CozinhaInputModel;
import com.murillo.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputModel cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputModel cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }

}
