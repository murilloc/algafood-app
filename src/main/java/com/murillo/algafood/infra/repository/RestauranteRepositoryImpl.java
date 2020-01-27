package com.murillo.algafood.infra.repository;

import com.murillo.algafood.domain.model.Restaurante;
import com.murillo.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Restaurante> listar() {

        TypedQuery<Restaurante> query = entityManager.createQuery("select r from Restaurante r", Restaurante.class);
        return query.getResultList();
    }

    @Override
    public Restaurante buscar(Long id) {
        entityManager.find(Restaurante.class, id);
        return null;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return entityManager.merge(restaurante);
    }

    @Override
    public void remover(Long id) {
        Restaurante  restaurante = buscar(id);
        entityManager.remove(restaurante);

    }
}
