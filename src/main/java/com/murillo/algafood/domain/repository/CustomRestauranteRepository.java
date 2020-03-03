package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface CustomRestauranteRepository {
    List<Restaurante> encontrarPorNomeETaxa(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
