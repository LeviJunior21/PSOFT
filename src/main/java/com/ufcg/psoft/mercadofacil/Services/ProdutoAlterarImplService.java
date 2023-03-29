package com.ufcg.psoft.mercadofacil.Services;

import com.ufcg.psoft.mercadofacil.Repository.ProdutoRepository;
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
            throw new RuntimeException("Preco invalido!");
        }
        return produtoRepository.update(produtoAlterado);
    }

    private boolean validaCodigoBarra(String codigoBarra) {
        int soma = 0;
        boolean resultado = false;

        String[] codigo = codigoBarra.split("");
        for (int i = codigo.length - 2; i >= 1 ; i--) {
            if (i % 2 == 0) soma += 3 * Integer.parseInt(codigo[i]);
            else soma += Integer.parseInt(codigo[i - 1]);
        }

        if ((soma + Integer.parseInt(codigo[12])) % 10 != 0) {
            resultado = true;
        }
        return resultado;
    }
}
