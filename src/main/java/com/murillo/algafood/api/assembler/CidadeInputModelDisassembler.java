package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.CidadeInputModel;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputModel cidadeInputModel) {
        return modelMapper.map(cidadeInputModel, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputModel cidadeInputModel, Cidade cidade) {

        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInputModel, cidade);
    }

}
