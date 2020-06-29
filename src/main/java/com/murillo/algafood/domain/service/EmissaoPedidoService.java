package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.exception.PedidoNaoEncontradoException;
import com.murillo.algafood.domain.model.*;
import com.murillo.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    CadastroRestauranteService cadastroRestaurante;
    @Autowired
    CadastroCidadeService cadastroCidade;
    @Autowired
    CadastroUsuarioService cadastroUsuario;
    @Autowired
    CadastroFormaPagamentoService cadastroFormaPagamento;
    @Autowired
    CadastroProdutoService cadastroProduto;


    public Pedido buscarOuFalhar(String codigo) {
        return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
    }


    @Transactional
    public Pedido emitir(Pedido pedido) {

        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calculaValorTotal();

        return pedidoRepository.save(pedido);

    }


    private void validarPedido(Pedido pedido) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("O Restaurante %d não possui a forma de pagamento  %s",
                    restaurante.getId(),
                    formaPagamento.getDescricao()));
        }


    }

    private void validarItens(Pedido pedido) {
        pedido.getItensPedido().forEach(item -> {
            Produto produto = cadastroProduto.buscarOuFalhar(item.getProduto().getId(), pedido.getRestaurante().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

}
