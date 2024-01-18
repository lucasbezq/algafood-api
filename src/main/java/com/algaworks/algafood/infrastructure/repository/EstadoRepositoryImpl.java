package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {


    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        return manager.createQuery("FROM Estado", Estado.class)
                .getResultList();
    }

    @Transactional
    public Estado adicionar(Estado Estado) {
        return manager.merge(Estado);
    }

    @Override
    public Estado buscar(Long id) {
        return manager.find(Estado.class, id);
    }

    @Override
    public Estado salvar(Estado Estado) {
        return null;
    }

    @Transactional
    public void remover (Estado Estado) {
        Estado = buscar(Estado.getId());
        manager.remove(Estado);
    }
}
