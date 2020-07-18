package com.murillo.algafood.api.controller;


import com.murillo.algafood.api.assembler.FotoProdutoInputModelAssembler;
import com.murillo.algafood.api.model.input.FotoProdutoInputModel;
import com.murillo.algafood.api.model.output.FotoProdutoOutputModel;
import com.murillo.algafood.domain.model.FotoProduto;
import com.murillo.algafood.domain.model.Produto;
import com.murillo.algafood.domain.service.CadastroProdutoService;
import com.murillo.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private CadastroProdutoService cadastroProduto;
    @Autowired
    private FotoProdutoInputModelAssembler fotoProdutoInputModelAssembler;


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoOutputModel atualizarFoto(@PathVariable Long restauranteId,
                                                @PathVariable Long produtoId,
                                                @Valid FotoProdutoInputModel fotoProdutoInput) throws IOException {

        Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoProduto = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());
        return fotoProdutoInputModelAssembler.toOutputModel(fotoProduto);
    }


}
