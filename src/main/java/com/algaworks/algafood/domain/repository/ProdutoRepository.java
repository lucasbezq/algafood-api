package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);
    List<Produto> findByRestaurante(Restaurante restaurante);
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);


}
