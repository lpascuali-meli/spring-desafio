package com.spring.challenge.controllers;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.services.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService articleService;

    @Autowired
    private MockMvc mockMvc;

    private static List<ProductDto> products;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {

        products =
                objectMapper.readValue(new File("src/main/resources/testProducts.json"),
                        new TypeReference<List<ProductDto>>() {
                        });
    }

    @Test
    void getProductsByFilters() throws Exception {

        //mock service
        when(articleService.getProductsByFilters(any())).thenReturn(products);

        //get /articles
        //map into objects
        MvcResult mvcResult = mockMvc.perform(get("/articles"))
                .andExpect(status().isOk()).andReturn();

        List<ProductDto> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ProductDto>>() {
                });
        //assert equals
        Assertions.assertEquals(responseArticles, products);
    }
}