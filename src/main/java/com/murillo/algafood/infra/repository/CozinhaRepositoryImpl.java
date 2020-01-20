package com.murillo.algafood.infra.repository;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Cozinha> listar() {

        TypedQuery<Cozinha> query = entityManager.createQuery("select c from Cozinha c", Cozinha.class);
        return query.getResultList();
    }


    @Override
    public Cozinha buscar(Long id) {

        return entityManager.find(Cozinha.class, id);
    }

    @Transactional
    @Override
    public Cozinha salvar(Cozinha cozinha) {

        return entityManager.merge(cozinha);
    }


    @Override
    @Transactional
    public void remover(Cozinha cozinha) {

        cozinha = buscar(cozinha.getId());
        entityManager.remove(cozinha);
    }


}
