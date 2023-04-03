package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.Repository.ProdutoRepository;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador de Produtos")
public class ProdutoV1Controller {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    Produto produto;
    @BeforeEach
    void setup() {
        produto = produtoRepository.find(10L);
    }

    @Test
    @DisplayName("Altera o produto com outro nome.")
    void alteroProdutoComNomeValido() throws Exception {
        produto.setNome("Produto Dez Alterado");
        String produtoModificadoJSONString = mockMvc.perform(put("/v1/produtos/"  + produto.getId())
                        .contentType(MediaType.APPLICATION_JSON) // Tipo de conteúdo que vou passar no put, que é um JSON UTF-8
                        .content(objectMapper.writeValueAsString(produto))) // Produto transformado para uma String, pois não pode passar produto via HTTP, só texto (feito pelo objectMapper)
                .andExpect(status().isOk()) // Pego o status com o andExpect que é feito após o perform
                .andDo(print()) // Ver a execução no console
                .andReturn().getResponse().getContentAsString(); // Retorna uma resposta como string.
        // Recebo a string e transformo em produto
        Produto produtoModificado = objectMapper.readValue(produtoModificadoJSONString, Produto.ProdutoBuilder.class).build();
        assertEquals("Produto Dez Alterado", produtoModificado.getNome());
    }
}