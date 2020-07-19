package com.murillo.algafood.api.controller;


import com.murillo.algafood.api.assembler.FotoProdutoInputModelAssembler;
import com.murillo.algafood.api.model.input.FotoProdutoInputModel;
import com.murillo.algafood.api.model.output.FotoProdutoOutputModel;
import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.model.FotoProduto;
import com.murillo.algafood.domain.model.Produto;
import com.murillo.algafood.domain.service.CadastroProdutoService;
import com.murillo.algafood.domain.service.CatalogoFotoProdutoService;
import com.murillo.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private CadastroProdutoService cadastroProduto;
    @Autowired
    private FotoProdutoInputModelAssembler fotoProdutoInputModelAssembler;

    @Autowired
    private FotoStorageService fotoStorage;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoOutputModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        FotoProduto foto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

        return fotoProdutoInputModelAssembler.toOutputModel(foto);
    }


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


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarFoto(@PathVariable Long restauranteId,
                            @PathVariable Long produtoId) {

        catalogoFotoProduto.excluir(restauranteId, produtoId);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
                                                          @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto foto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(foto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            InputStream inputstream = fotoStorage.recuperar(foto.getNomeArquivo());
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(inputstream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypesAceita -> mediaTypesAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }

    }


}
