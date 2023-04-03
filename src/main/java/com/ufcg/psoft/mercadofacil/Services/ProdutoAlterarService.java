package com.ufcg.psoft.mercadofacil.Services;

import com.ufcg.psoft.mercadofacil.model.Produto;

@FunctionalInterface
public interface ProdutoAlterarService {
    public Produto alterar(Produto produtoAlterado);
}
