package com.murillo.algafood.infra.repository.service;

import com.murillo.algafood.domain.filter.VendaDiariaFilter;
import com.murillo.algafood.domain.model.Pedido;
import com.murillo.algafood.domain.model.StatusPedido;
import com.murillo.algafood.domain.model.dto.VendaDiaria;
import com.murillo.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

        var predicates = new ArrayList<Predicate>();

        var statusValidos = new ArrayList<StatusPedido>();
        statusValidos.add(StatusPedido.CONFIRMADO);
        statusValidos.add(StatusPedido.ENTREGUE);

        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionConvertTzDataCriacao = builder
                .function("convert_tz", Date.class, root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));


        var functionDateDataCriacao = builder
                .function("date", LocalDate.class, functionConvertTzDataCriacao);

        predicates.add(builder.in(root.get("statusPedido")).value(statusValidos));

        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("id"), filtro.getRestauranteId()));
        }

        var selection = builder.
                construct(VendaDiaria.class,
                        functionDateDataCriacao,
                        builder.count(root.get("id")), builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.where(builder.and(predicates.toArray(new Predicate[0])));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
