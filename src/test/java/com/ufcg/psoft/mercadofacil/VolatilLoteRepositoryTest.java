package com.ufcg.psoft.mercadofacil;

import com.ufcg.psoft.mercadofacil.Repository.LoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ufcg.psoft.mercadofacil.Repository.VolatilLoteRepository;
import com.ufcg.psoft.mercadofacil.Services.Lote;
import com.ufcg.psoft.mercadofacil.Services.Produto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@SpringBootTest
class VolatilLoteRepositoryTest {

    @Autowired
    LoteRepository<Lote, Long> driver;

    Lote lote;
    Lote resultado;
    Produto produto;


    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }


    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }


    /**
     */
    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote() {
        resultado = driver.save(lote);


        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    /**
     * O método salva o lote mas o retorno não é o esperado pois ele retorna o primeiro lote da lista
     * e não o lote adicionado que é o segundo lote.
     * Além disso, o tamanho da lista de lotes está de acordo com a quantidade de lotes adicionados.
     */
    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados.")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);


        resultado = driver.save(loteExtra);


        assertEquals(2, driver.findAll().size());
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);


    }

    /**
     * O método update apaga todos os lotes e adiciona o lote que deveria ser adicionado substituindo outro
     * com o mesmo ID.
     * O loteExtra2 tem o mesmo ID do lote que deveria ser substituido.
     * Deveria existir 2 lotes, mas existe só um.
     */
    @Test
    @DisplayName("Atualiza um lote com o mesmo ID de algum já adicionado")
    void atualizaUmLoteComMesmoIDDeAlgumJaAdicionado() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(4L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(1L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(lote);
        driver.save(loteExtra1);

        driver.update(loteExtra2);
        assertEquals(driver.findAll().size(), 2);
    }

    @Test
    @DisplayName("Adiciona e conta os lotes adicionados")
    void adicionaLotesEContaOTamanho() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(2L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(3L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(lote);
        driver.save(loteExtra1);
        driver.save(loteExtra2);

        assertEquals(driver.findAll().size(), 3);
    }

    @Test
    @DisplayName("Deleta todos os lotes adicionados")
    void deletaTodosLotes() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(3L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(3L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(loteExtra1);
        driver.save(loteExtra2);

        driver.deleteAll();
        assertEquals(driver.findAll().size(), 0);
    }

    /**
     * O método não faz o esperado pois, ele deleta todos os lotes envés de deletar o lote repassado para o método.
     * Foram adicionados 3 lotes e esperava-se a remoção de um lote especifico mas foram removidos todos os lotes.
     */
    @Test
    @DisplayName("Deleta um lote na lista de lotes")
    void deletaUmLoteNaListaDeLotes() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(2L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(3L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(lote);
        driver.save(loteExtra1);
        driver.save(loteExtra2);

        driver.delete(lote);

        assertEquals(driver.findAll().size(), 2);
    }

    @Test
    @DisplayName("Verifica se a lista de lotes são iguais aos pedidos para adicionar na lista.")
    void verificaSeAListaDeLotesSaoIguaisAosPedidosParaAdicionar() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(3L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(3L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(lote);
        driver.save(loteExtra1);
        driver.save(loteExtra2);


        ArrayList<Lote> novaLista = new ArrayList<Lote>();
        novaLista.add(lote);
        novaLista.add(loteExtra1);
        novaLista.add(loteExtra2);
        assertEquals(driver.findAll(), novaLista);
    }

    /**
     * O método apresenta acerto mas está errado pois, além de todos os lotes serem removidos envés de atualiza-los,
     * é adicionado o lote repassado e o lote retornado é o primeiro a ser adicionado na lista envés do ultmo lote
     * adicionado na lista.
     * Para provar isso verificamos o tamanho da lista de lotes no sistema que deveria ser 2 pois o ID do lote é igual ao
     * do loteExtra2. O retorno foi de 1 lote e não 2 lotes.
     */
    @Test
    @DisplayName("Verifica se a lista de lotes são iguais aos pedidos para atualizar na lista.")
    void verificaSeOLoteRetornadoEhOUltimoAdicionadoAoAtualzar() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(3L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(1L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(lote);
        driver.save(loteExtra1);
        driver.update(loteExtra2);

        assertEquals(driver.update(loteExtra2), loteExtra2);
        assertEquals(driver.findAll().size(), 2);
    }

    /**
     * O método apresenta erro pois, ele envés de percorrer a lista buscando pelo ID, ele faz um get do ID
     * que é do tipo Long e sempre a lista será menor que o tamanho do ID do tipo Long repassado, causando o erro
     * IndexOutOfBoundException.
     */
    @Test
    @DisplayName("Verifica se a procura de um lote pelo ID retorna o lote esperado")
    void verificaSeAProcuraDeUmLotePeloIdRetornaOLoteEsperado() {
        Produto produtoExtra1 = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();

        Lote loteExtra1 = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra1)
                .build();


        Produto produtoExtra2 = Produto.builder()
                .id(2L)
                .nome("Produto Extra 2")
                .codigoBarra("9876543212213")
                .fabricante("Fabricante Extra 2")
                .preco(14.36)
                .build();

        Lote loteExtra2 = Lote.builder()
                .id(4L)
                .numeroDeItens(50)
                .produto(produtoExtra2)
                .build();

        driver.save(lote);
        driver.save(loteExtra1);
        driver.save(loteExtra2);

        assertEquals(driver.find(loteExtra2.getId()), loteExtra2);
    }
}