package com.spring.challenge.dtos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductDtoTest {

    public static ArrayList<ProductDto> getTestProducts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/testProducts.json"), new TypeReference<ArrayList<ProductDto>>() {
        });
    }

    public static ProductDto getProductIdOne() {
        return new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4);
    }

    public static ArrayList<ProductDto> getExpectedProductsCategoryHerramientas() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsForCategoryAndFreeShipmentFilter() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 9600.0, 5, false, 4));
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        list.add(new ProductDto(5, "Samsung Galaxy s21 +", "Celulares", "Samsung", 130000.0, 7, true, 4));
        list.add(new ProductDto(6, "Samsung Galaxy s21", "Celulares", "Samsung", 100000.0, 7, true, 4));
        list.add(new ProductDto(7, "Short", "Indumentaria", "Lacoste", 2400.0, 10, true, 4));
        list.add(new ProductDto(8, "Remerita", "Indumentaria", "Bensimon", 1500.0, 20, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getExpectedProductsForCategoryAndFreeShipmentFilter() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsAlphabeticallyDesordered() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 9600.0, 5, false, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsAlphabeticallyOrderedDes() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 9600.0, 5, false, 4));
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsAlphabeticallyOrderedAsc() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 9600.0, 5, false, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsPriceDesordered() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 100.0, 5, false, 4));
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsPriceOrderedLowerToHigher() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 100.0, 5, false, 4));
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        return list;
    }

    public static ArrayList<ProductDto> getProductsPriceOrderedHigherToLower() {
        ArrayList<ProductDto> list = new ArrayList<>();
        list.add(new ProductDto(4, "Samsung Galaxy s21 Ultra", "Celulares", "Samsung", 150000.0, 7, true, 4));
        list.add(new ProductDto(2, "Taladro", "Herramientas", "Black & Decker", 12500.0, 7, true, 4));
        list.add(new ProductDto(1, "Desmalezadora", "Herramientas", "Makita", 9600.0, 5, true, 4));
        list.add(new ProductDto(3, "Soldadora", "Herramientas", "Makita", 100.0, 5, false, 4));
        return list;
    }
}