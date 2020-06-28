package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.PedidoOutputModel;
import com.murillo.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoOutputModel toOutputModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoOutputModel.class);
    }

    public List<PedidoOutputModel> toOutputModelCollection(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toOutputModel).collect(Collectors.toList());
    }
}
