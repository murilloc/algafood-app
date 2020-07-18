package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.FotoProdutoOutputModel;
import com.murillo.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class FotoProdutoInputAssembler {


    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoOutputModel toOutputModel(FotoProduto fotoProduto) {

        return modelMapper.map(fotoProduto, FotoProdutoOutputModel.class);
    }


    public List<FotoProdutoOutputModel> toOutputModelCollection(List<FotoProduto> fotos) {

        return fotos.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }

}
