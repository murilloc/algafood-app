package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.PedidoInputModelAssembler;
import com.murillo.algafood.api.assembler.PedidoInputModelDisassembler;
import com.murillo.algafood.api.assembler.PedidoResumoInputModelAssembler;
import com.murillo.algafood.api.model.input.PedidoInputModel;
import com.murillo.algafood.api.model.output.PedidoOutputModel;
import com.murillo.algafood.api.model.output.PedidoResumoOutputModel;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.model.Usuario;
import com.murillo.algafood.domain.repository.PedidoRepository;
import com.murillo.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private PedidoInputModelDisassembler pedidoInputModelDisassembler;


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

    @PutMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoOutputModel emitirPedido(@Valid @RequestBody PedidoInputModel pedidoInput) {

        try {
            Pedido novoPedido = pedidoInputModelDisassembler.toDomainObject(pedidoInput);

            // TODO recuperar usuário logado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);
            novoPedido = cadastroPedido.emitir(novoPedido);

            return pedidoInputModelAssembler.toOutputModel(novoPedido);
        } catch (EntidadeNaoEncontradaException ene) {
            throw new NegocioException(ene.getMessage(), ene);
        }
    }
}
