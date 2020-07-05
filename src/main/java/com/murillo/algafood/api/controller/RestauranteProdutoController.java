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
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

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
    public List<ProdutoOutputModel> listar(@RequestParam(value = "incluir-inativos", required = false) boolean incluirInativos, @PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        List<Produto> produtos;

        if (incluirInativos) {
            produtos = produtoRepository.findAllByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoInputModelAssembler.toOutputModelCollection(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoOutputModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        cadastroRestaurante.buscarOuFalhar(restauranteId);
        Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);

        return produtoInputModelAssembler.toOutputModel(produto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoOutputModel salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputModel produtoInput) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        Produto novoProduto = produtoInputModelDisassembler.toDomainObject(produtoInput);
        novoProduto.setRestaurante(restaurante);
        novoProduto = cadastroProduto.salvar(novoProduto);

        return produtoInputModelAssembler.toOutputModel(novoProduto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoOutputModel alterar(@PathVariable Long restauranteId,
                                      @PathVariable Long produtoId,
                                      @RequestBody @Valid ProdutoInputModel produtoInput) {

        Produto produtoCadastrado = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
        produtoInputModelDisassembler.copyToDomainObject(produtoInput, produtoCadastrado);
        produtoCadastrado = cadastroProduto.salvar(produtoCadastrado);

        return produtoInputModelAssembler.toOutputModel(produtoCadastrado);
    }


    @DeleteMapping("{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        cadastroProduto.excluir(produtoId, restauranteId);

    }
}
