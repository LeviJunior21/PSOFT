package com.ufcg.psoft.mercadofacil.Services;

import com.ufcg.psoft.mercadofacil.Repository.ProdutoRepository;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoAlterarImplService implements ProdutoAlterarService {
    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;
    @Override
    public Produto alterar(Produto produtoAlterado) {
        if(produtoAlterado.getPreco()<=0) {
            throw new RuntimeException("Preco invalido!");
        } else
        if (validaCodigoBarra(produtoAlterado.getCodigoBarra()) == false) {
            throw new RuntimeException("Código de barras inválido!");
        }
        return produtoRepository.update(produtoAlterado);
    }

    public static boolean validaCodigoBarra(String codigoBarra) {
        int soma = 0;
        boolean resultado = false;

        String[] codigo = codigoBarra.split("");
        for (int i = codigo.length - 2; i >= 1 ; i-=2) {
            soma += 3 * Integer.parseInt(codigo[i]);
            soma += Integer.parseInt(codigo[i - 1]);
        }
        soma = soma + Integer.parseInt(codigo[codigo.length - 1]);
        if (soma % 10 == 0) {
            resultado = true;
        }
        return resultado;
    }
}
