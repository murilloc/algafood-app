package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.CidadeOutputModel;
import com.murillo.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeOutputModel toOutputModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeOutputModel.class);
    }

    public List<CidadeOutputModel> toOutputModelCollection(List<Cidade> cidades) {

        return cidades.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }

}
