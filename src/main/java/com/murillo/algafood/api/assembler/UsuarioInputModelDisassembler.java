package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.UsuarioInputModel;
import com.murillo.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsuarioInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInputModel usuarionInputModel) {
        return modelMapper.map(usuarionInputModel, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInputModel usuarioInputModel, Usuario usuario) {
        usuario.setGrupos(new ArrayList<>());
        modelMapper.map(usuarioInputModel, usuario);
    }


}
