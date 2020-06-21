package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.PermissaoInputModel;
import com.murillo.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao toDomainObject(PermissaoInputModel permissaoInput) {

        return modelMapper.map(permissaoInput, Permissao.class);
    }


    public void copyToDomainObject(PermissaoInputModel permissaoInput, Permissao permissao) {

        modelMapper.map(permissaoInput, permissao);
    }
}
