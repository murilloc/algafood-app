package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.PedidoInputModel;
import com.murillo.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInputModel pedidoInput) {

        return modelMapper.map(pedidoInput, Pedido.class);
    }


    public void copyToDomainObject(PedidoInputModel pedidoInput, Pedido pedido) {

        modelMapper.map(pedidoInput, pedido);

    }
}
