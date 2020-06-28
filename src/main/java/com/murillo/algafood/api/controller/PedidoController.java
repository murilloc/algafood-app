package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.PedidoInputModelAssembler;
import com.murillo.algafood.api.assembler.PedidoResumoInputModelAssembler;
import com.murillo.algafood.api.model.output.PedidoOutputModel;
import com.murillo.algafood.api.model.output.PedidoResumoOutputModel;
import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.repository.PedidoRepository;
import com.murillo.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoResumoInputModelAssembler pedidoResumoInputModelAssembler;

    @Autowired
    private PedidoInputModelAssembler pedidoInputModelAssembler;

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;


    @GetMapping
    public List<PedidoResumoOutputModel> listar() {

        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoResumoInputModelAssembler.toOutputModelCollection(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoOutputModel buscar(@PathVariable Long pedidoId) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(pedidoId);

        return pedidoInputModelAssembler.toOutputModel(pedido);
    }


}
