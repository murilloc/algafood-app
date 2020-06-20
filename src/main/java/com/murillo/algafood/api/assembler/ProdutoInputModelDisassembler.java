package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.ProdutoInputModel;
import com.murillo.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInputModel produtoInput) {

        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInputModel produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }

}
