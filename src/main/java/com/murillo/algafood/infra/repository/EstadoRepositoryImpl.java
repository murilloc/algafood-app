package com.murillo.algafood.infra.repository;

import com.murillo.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.murillo.algafood.domain.model.Estado;
import com.murillo.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {

        TypedQuery<Estado> query = entityManager.createQuery("Select e from Estado e", Estado.class);
        return query.getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        Estado estado = entityManager.find(Estado.class, id);
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe estado com código %d", id));
        }

        return estado;
    }

    @Override
    @Transactional
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Estado estado = entityManager.find(Estado.class, id);
        if (estado == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(estado);
    }
}
