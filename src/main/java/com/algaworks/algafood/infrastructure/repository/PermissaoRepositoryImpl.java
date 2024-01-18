package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {


    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permissao> listar() {
        return manager.createQuery("FROM Permissao", Permissao.class)
                .getResultList();
    }

    @Transactional
    public Permissao adicionar(Permissao Permissao) {
        return manager.merge(Permissao);
    }

    @Override
    public Permissao buscar(Long id) {
        return manager.find(Permissao.class, id);
    }

    @Override
    public Permissao salvar(Permissao Permissao) {
        return null;
    }

    @Transactional
    public void remover (Permissao Permissao) {
        Permissao = buscar(Permissao.getId());
        manager.remove(Permissao);
    }
}
