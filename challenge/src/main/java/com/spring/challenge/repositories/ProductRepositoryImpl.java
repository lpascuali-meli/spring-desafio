package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ProductDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ArrayList<ProductDto> products;

    public ProductRepositoryImpl(){
        products = new ArrayList<>();
        loadDataBase();
    }

    @Override
    public List<ProductDto> getProductsByFilters(Map<String, String> filters) {
        return products.stream()
                .filter(product -> {
                   boolean matches = true;
                   if (filters.get("name") != null) {
                       matches = product.getName().equals(filters.get("name"));
                   }
                   if (filters.get("brand") != null) {
                       matches = matches && product.getBrand().equals(filters.get("brand"));
                   }
                   if (filters.get("category") != null) {
                       matches = matches && product.getCategory().equals(filters.get("category"));
                   }
                   if (filters.get("freeShipping") != null) {
                       matches = matches && product.getFreeShipping().equals(Boolean.parseBoolean(filters.get("freeShipping")));
                   }
                   if (filters.get("prestige") != null) {
                       matches = matches && product.getPrestige().equals(Integer.parseInt(filters.get("prestige")));
                   }
                   return matches;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsForPurchase(List<Integer> ids) {
        return products.stream()
            .filter(product -> {
                return ids.contains(product.getId());
            }).collect(Collectors.toList());
    }

    public void loadDataBase() {
        String COMMA_DELIMITER = ",";
        List<List<String>> result = null;
        try {
            result = Files.readAllLines(Paths.get(loadFile("dbProductos.csv").getAbsolutePath()))
                    .stream()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < Objects.requireNonNull(result).size(); i++) {
            List<String> parameters = result.get(i);
            ProductDto productDto = new ProductDto();
            productDto.setId(Integer.parseInt(parameters.get(0)));
            productDto.setName(parameters.get(1));
            productDto.setCategory(parameters.get(2));
            productDto.setBrand(parameters.get(3));
            productDto.setPrice(Double.parseDouble(parameters.get(4).replace("$", "").replace(".", "")));
            productDto.setQuantity(Integer.parseInt(parameters.get(5)));
            productDto.setFreeShipping(parameters.get(6).equals("SI"));
            productDto.setPrestige(parameters.get(7).split("").length);
            products.add(productDto);
        }
    }

    public File loadFile(String fileName) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

}