package com.spring.challenge.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.challenge.dtos.ProductDtoTest;
import com.spring.challenge.repositories.ProductRepository;
import com.spring.challenge.services.ProductService;
import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    ProductService productService;

    @BeforeEach
    void setUp() throws IOException {
        initMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getAllProducts() throws ApiException, IOException {

        when(productRepository.getProducts()).thenReturn(ProductDtoTest.getTestProducts());

        List<ProductDto> returnedProducts = productService.getProductsByFilters(new HashMap<>());

        verify(productRepository, atLeast(1)).getProducts();
        assertThat(returnedProducts).isEqualTo(ProductDtoTest.getTestProducts());
    }

    @Test
    void getProductsByCategory() throws ApiException, IOException {

            when(productRepository.getProducts()).thenReturn(ProductDtoTest.getTestProducts());

        HashMap<String, String> filters = new HashMap<>();
        filters.put("category", "Herramientas");

        List<ProductDto> returnedProducts = productService.getProductsByFilters(filters);

        verify(productRepository, atLeast(1)).getProducts();
        assertThat(returnedProducts).isEqualTo(ProductDtoTest.getExpectedProductsCategoryHerramientas());
    }


    @Test
    void getProductsByFilters() throws ApiException {

        when(productRepository.getProducts()).thenReturn(ProductDtoTest.getProductsForCategoryAndFreeShipmentFilter());

        HashMap<String, String> filters = new HashMap<>();
        filters.put("category", "Herramientas");
        filters.put("freeShipping", "true");

        List<ProductDto> returnedProducts = productService.getProductsByFilters(filters);

        verify(productRepository, atLeast(1)).getProducts();
        assertThat(returnedProducts).isEqualTo(ProductDtoTest.getExpectedProductsForCategoryAndFreeShipmentFilter());
    }

    @Test
    void getProductsOrderByPriceDesc() throws ApiException {

        when(productRepository.getProducts()).thenReturn(ProductDtoTest.getProductsPriceDesordered());

        HashMap<String, String> filters = new HashMap<>();
        filters.put("order", "2");

        List<ProductDto> returnedProducts = productService.getProductsByFilters(filters);

        verify(productRepository, atLeast(1)).getProducts();
        assertThat(returnedProducts).isEqualTo(ProductDtoTest.getProductsPriceOrderedHigherToLower());
    }

    @Test
    void getProductsOrderByPriceAsc() throws ApiException {

        when(productRepository.getProducts()).thenReturn(ProductDtoTest.getProductsPriceDesordered());

        HashMap<String, String> filters = new HashMap<>();
        filters.put("order", "3");

        List<ProductDto> returnedProducts = productService.getProductsByFilters(filters);

        verify(productRepository, atLeast(1)).getProducts();
        assertThat(returnedProducts).isEqualTo(ProductDtoTest.getProductsPriceOrderedLowerToHigher());
    }

}