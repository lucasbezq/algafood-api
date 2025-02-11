package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return entityManager.merge(foto);
    }

    @Transactional
    @Override
    public void delete(FotoProduto foto) {
        entityManager.remove(foto);
    }
}
