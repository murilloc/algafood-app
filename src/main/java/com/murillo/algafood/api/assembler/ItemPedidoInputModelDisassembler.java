package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.ItemPedidoInputModel;
import com.murillo.algafood.domain.model.ItemPedido;
import com.murillo.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(ItemPedidoInputModel itemPedidoInput) {

        return modelMapper.map(itemPedidoInput, Pedido.class);
    }


    public void copyToDomainObject(ItemPedidoInputModel itemPedidoInput, ItemPedido itemePdido) {

        modelMapper.map(itemPedidoInput, itemePdido);

    }
}
