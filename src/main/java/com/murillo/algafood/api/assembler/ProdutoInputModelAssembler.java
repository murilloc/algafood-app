package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.ProdutoOutputModel;
import com.murillo.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoOutputModel toOutputModel(Produto produto) {

        return modelMapper.map(produto, ProdutoOutputModel.class);

    }

    public List<ProdutoOutputModel> toOutputModelCollection(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }
}
