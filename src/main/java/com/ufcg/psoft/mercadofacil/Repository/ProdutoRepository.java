package com.ufcg.psoft.mercadofacil.Repository;

import com.ufcg.psoft.mercadofacil.model.Produto;
import org.yaml.snakeyaml.tokens.Token;

import java.util.List;

public interface ProdutoRepository<T,I> {
    T save(T lote);
    T find(Long id);
    List<T> findAll();
    T update(T lote);
    void delete(T lote);
    void deleteAll();
}
