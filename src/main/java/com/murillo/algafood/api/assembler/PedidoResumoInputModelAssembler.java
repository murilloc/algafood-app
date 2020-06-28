package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.PedidoResumoOutputModel;
import com.murillo.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoOutputModel toOutputModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoOutputModel.class);
    }

    public List<PedidoResumoOutputModel> toOutputModelCollection(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toOutputModel).collect(Collectors.toList());
    }
}
