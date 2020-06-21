package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.UsuarioOutputModel;
import com.murillo.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioOutputModel toOutputModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioOutputModel.class);
    }


    public List<UsuarioOutputModel> toOutputModelCollection(Collection<Usuario> usuarios) {
        return usuarios.stream().map(this::toOutputModel).collect(Collectors.toList());
    }
}
