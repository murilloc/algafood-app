package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.ProdutoInputModelAssembler;
import com.murillo.algafood.api.assembler.ProdutoInputModelDisassembler;
import com.murillo.algafood.api.model.input.ProdutoInputModel;
import com.murillo.algafood.api.model.output.ProdutoOutputModel;
import com.murillo.algafood.domain.model.Produto;
import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.ProdutoRepository;
import com.murillo.algafood.domain.service.CadastroProdutoService;
import com.murillo.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoInputModelAssembler produtoInputModelAssembler;

    @Autowired
    private ProdutoInputModelDisassembler produtoInputModelDisassembler;


    @GetMapping()
    public List<ProdutoOutputModel> listar(@PathVariable Long idRestaurante) {

        cadastroRestaurante.buscarOuFalhar(idRestaurante);
        List<Produto> produtos = produtoRepository.findByRestauranteId(idRestaurante);

        return produtoInputModelAssembler.toOutputModelCollection(produtos);
    }

    @GetMapping("/{idProduto}")
    public ProdutoOutputModel buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {

        cadastroRestaurante.buscarOuFalhar(idRestaurante);
        Produto produto = cadastroProduto.buscarOuFalhar(idProduto, idRestaurante);

        return produtoInputModelAssembler.toOutputModel(produto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoOutputModel salvar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInputModel produtoInput) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(idRestaurante);
        Produto novoProduto = produtoInputModelDisassembler.toDomainObject(produtoInput);
        novoProduto.setRestaurante(restaurante);
        novoProduto = cadastroProduto.salvar(novoProduto);

        return produtoInputModelAssembler.toOutputModel(novoProduto);
    }

    @PutMapping("/{idProduto}")
    public ProdutoOutputModel alterar(@PathVariable Long idRestaurante,
                                      @PathVariable Long idProduto,
                                      @RequestBody @Valid ProdutoInputModel produtoInput) {

        Produto produtoCadastrado = cadastroProduto.buscarOuFalhar(idProduto, idRestaurante);
        produtoInputModelDisassembler.copyToDomainObject(produtoInput, produtoCadastrado);
        produtoCadastrado = cadastroProduto.salvar(produtoCadastrado);

        return produtoInputModelAssembler.toOutputModel(produtoCadastrado);
    }


    @DeleteMapping("{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {

        cadastroProduto.excluir(idProduto, idRestaurante);

    }
}
