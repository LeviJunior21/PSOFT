package com.ufcg.psoft.mercadofacil.Repository;

import com.ufcg.psoft.mercadofacil.Services.Produto;

public interface ProdutoRepository<T,I> {
    public T find(I l);

    public T update(T produto);

    Produto save(Produto produto);
}
