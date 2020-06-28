package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.ItemPedidoOutputModel;
import com.murillo.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPedidoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoOutputModel toOutputModel(ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoOutputModel.class);
    }

    public List<ItemPedidoOutputModel> toOutputModelCollection(List<ItemPedido> itensPedidos) {
        return itensPedidos.stream()
                .map(this::toOutputModel).collect(Collectors.toList());
    }
}
