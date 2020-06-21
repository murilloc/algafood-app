package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.PermissaoOutputModel;
import com.murillo.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public PermissaoOutputModel toOutoutModel(Permissao permissao) {

        return modelMapper.map(permissao, PermissaoOutputModel.class);
    }

    public List<PermissaoOutputModel> toOutputModelCollection(List<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toOutoutModel)
                .collect(Collectors.toList());
    }
}
