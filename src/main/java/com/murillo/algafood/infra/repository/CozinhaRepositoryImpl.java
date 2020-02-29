package com.murillo.algafood.infra.repository;

import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Cozinha> listar() {

        TypedQuery<Cozinha> query = entityManager.createQuery("select c from Cozinha c", Cozinha.class);
        return query.getResultList();
    }

    @Override
    public List<Cozinha> consultaPorNome(String nome) {
        return entityManager.createQuery("select c from Cozinha c where c.nome like :nome", Cozinha.class).setParameter("nome","%"+ nome + "%").getResultList();
    }


    @Override
    public Cozinha buscar(Long id) {

        return entityManager.find(Cozinha.class, id);
    }


    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {

        return entityManager.merge(cozinha);
    }


    @Override
    @Transactional
    public void remover(Long id) {

        Cozinha cozinha = buscar(id);

        if (cozinha == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(cozinha);
    }

}
