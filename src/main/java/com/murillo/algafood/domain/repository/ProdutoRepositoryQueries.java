package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto fot);

    void delete(FotoProduto fotoProduto);
}
