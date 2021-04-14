package com.spring.challenge.dtos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDtoTest {

    public static ArrayList<ProductDto> getTestProducts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/testProducts.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getExpectedProductsCategoryHerramientas() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/filteredByCategoryProducts.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getProductsForCategoryAndFreeShipmentFilter() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/productsFilteredByCategoryAndProducts.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getProductsUnsorted() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/productsUnsorted.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getProductsAlphabeticallyOrderedDes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/productsOrderedAntiAlphabetically.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getProductsAlphabeticallyOrderedAsc() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/productsOrderedAlphabetically.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getProductsPriceOrderedLowerToHigher() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/productsOrderedByPriceAsc.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ArrayList<ProductDto> getProductsPriceOrderedHigherToLower() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/productsOrderedByPriceDesc.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static OrderForTicketDto getOrderToCreate() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/orderToCreate.json"), new TypeReference<OrderForTicketDto>() {
        });
    }

    public static TicketDto getTicketCreated() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/ticketCreated.json"), new TypeReference<TicketDto>() {
        });
    }

    public static HashMap<Integer, ProductDto> getOrderWithPrices() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/orderWithPrices.json"), new TypeReference<HashMap<Integer, ProductDto>>() {
        });
    }
    public static TicketDto getOrderInChart() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/orderInChart.json"), new TypeReference<TicketDto>() {
        });
    }
}