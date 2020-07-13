package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.filter.VendaDiariaFilter;
import com.murillo.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
