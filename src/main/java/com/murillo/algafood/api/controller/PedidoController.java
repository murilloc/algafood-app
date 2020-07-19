package com.murillo.algafood.api.controller;

import com.google.common.collect.ImmutableMap;
import com.murillo.algafood.api.assembler.PedidoInputModelAssembler;
import com.murillo.algafood.api.assembler.PedidoInputModelDisassembler;
import com.murillo.algafood.api.assembler.PedidoResumoInputModelAssembler;
import com.murillo.algafood.api.model.input.PedidoInputModel;
import com.murillo.algafood.api.model.output.PedidoOutputModel;
import com.murillo.algafood.api.model.output.PedidoResumoOutputModel;
import com.murillo.algafood.core.data.PageableTranslator;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.model.Usuario;
import com.murillo.algafood.domain.repository.PedidoRepository;
import com.murillo.algafood.domain.filter.PedidoFilter;
import com.murillo.algafood.domain.service.EmissaoPedidoService;
import com.murillo.algafood.infra.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private EmissaoPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoInputModelDisassembler pedidoInputModelDisassembler;


    @GetMapping
    public Page<PedidoResumoOutputModel> pesquisar(@PageableDefault PedidoFilter filtro, Pageable pageable) {

        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoOutputModel> pedidosResumeOutModel = pedidoResumoInputModelAssembler
                .toOutputModelCollection(pedidosPage.getContent());


        return new PageImpl<>(pedidosResumeOutModel, pageable, pedidosPage.getTotalElements());
    }


    private Pageable traduzirPageable(Pageable apiPageable) {

        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "nomeCliente", "cliente.nome",
                "restaurante.nome", "restaurante.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }


//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//
//        List<Pedido> pedidos = pedidoRepository.findAll();
//        List<PedidoResumoOutputModel> pedidosModel = pedidoResumoInputModelAssembler.toOutputModelCollection(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)){
//
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//
//    }

    @GetMapping("/{codigo}")
    public PedidoOutputModel buscar(@PathVariable String codigo) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(codigo);

        return pedidoInputModelAssembler.toOutputModel(pedido);
    }

    @PostMapping()
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
