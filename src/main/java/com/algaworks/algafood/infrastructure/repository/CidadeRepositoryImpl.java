package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {


    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        return manager.createQuery("FROM Cidade", Cidade.class)
                .getResultList();
    }

    @Transactional
    public Cidade adicionar(Cidade Cidade) {
        return manager.merge(Cidade);
    }

    @Override
    public Cidade buscar(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    public Cidade salvar(Cidade Cidade) {
        return null;
    }

    @Transactional
    public void remover (Cidade Cidade) {
        Cidade = buscar(Cidade.getId());
        manager.remove(Cidade);
    }
}
