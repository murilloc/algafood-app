package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.murillo.algafood.domain.model.Produto;
import com.murillo.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CadastroProdutoService {

    public static final String MSG_PRODUTO_EM_USO = "Não é possível excluir produto id %d pois está sendo usado no momento";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {

        return produtoRepository.save(produto);
    }

    public List<Produto> buscarOuFalhar(Long restauranteId) {
       return produtoRepository.findAllByRestauranteId(restauranteId);
    }

    public Produto buscarOuFalhar(Long produtoId, Long restauranteId) {
        return produtoRepository.findById(produtoId, restauranteId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    public void excluir(Long produtoId,Long restauranteId){
        try {
            buscarOuFalhar(produtoId,restauranteId);
            produtoRepository.deleteById(produtoId);
            produtoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(produtoId,restauranteId );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO,produtoId));
        }

    }

}
