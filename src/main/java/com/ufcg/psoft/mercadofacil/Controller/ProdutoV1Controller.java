package com.ufcg.psoft.mercadofacil.Controller;

import com.ufcg.psoft.mercadofacil.Services.ProdutoAlterarService;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(
        value = "/v1/produtos",
        produces = MediaType.APPLICATION_JSON_VALUE
)

public class ProdutoV1Controller {
    @Autowired
    ProdutoAlterarService produtoAtualizarService;

    @PutMapping("/{id}")
    public Produto atualizarProduto(
        @PathVariable Long id,
        @RequestBody Produto produto) {
        return produtoAtualizarService.alterar(produto);

    }

}
