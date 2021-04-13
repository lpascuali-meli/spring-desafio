package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.ProductForTicketDto;
import com.spring.challenge.dtos.TicketDto;
import com.spring.challenge.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SocketUtils;

import java.text.NumberFormat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ArrayList<ProductDto> products;
    private HashMap<Integer, ProductForTicketDto> productsInChart;

    public ProductRepositoryImpl(){
        products = new ArrayList<>();
        productsInChart = new HashMap<>();
        loadDataBase();
    }

    public ArrayList<ProductDto> getProducts() {
        return products;
    }


    /**
     * Dado el detalle de una orden de compra, retorna un mapa de todos los productos con sus precios.
     * @param ids Id de los productos a agregar al carritoo
     * @return Retorna un HashMap con los precios de los productos de la orden
     */
    @Override
    public HashMap<Integer, ProductDto> getProductsForPurchase(List<Integer> ids) {
        HashMap<Integer, ProductDto> productsFound = new HashMap<Integer, ProductDto>();
        // Busco cada producto de la orden en la base de datos de productos
        for (ProductDto product: getProducts()) {
            if (ids.contains(product.getProductId())) {
                productsFound.put(product.getProductId(), product);
            }
        }
        return productsFound;
    }

    /**
     *  Crea un ticket, combinando los productos nuevos con los del carrito,
     * actualizando las cantidades del stock y persistiendo los datos
     * @param ticket Ticket de orden actual (sin productos del carrito)
     * @return Ticket con productos del carrito
     * @throws ApiException
     */
    @Override
    public TicketDto mergeTicket(TicketDto ticket) throws ApiException {
        // Primero recorro los articulos de la nueva orden de compras
        for (ProductForTicketDto product: ticket.getArticles()) {
            ProductForTicketDto productInChart = productsInChart.get(product.getProductId());
            // Actualizo las cantidades en stock
            updateQuantity(product);
            if (productInChart != null) {
                // SI el producto ya estaba en el carrito le actualizo la cantidad
                productInChart.setQuantity(productInChart.getQuantity() + product.getQuantity());
            } else {
                // SI el producto no estaba en el carrito lo agrego
                productsInChart.put(product.getProductId(), product);
            }
        }
        List<ProductForTicketDto> articles = new ArrayList<>();
        // Recorro todos los articulos del carrito actualizado y los agrego en el detale del nuevo ticket
        for (Map.Entry<Integer, ProductForTicketDto> entry: productsInChart.entrySet()) {
            articles.add(entry.getValue());
        }
        ticket.setArticles(articles);
        // Persisto cantidades
         writeFile();
        return ticket;
    }

    /**
     * Actualiza las cantidades en stock de cada producto
     * @param productForTicket producto a actualizar cantidad
     */
    private void updateQuantity(ProductForTicketDto productForTicket) {
        for (ProductDto product: products) {
            if (product.getProductId().equals(productForTicket.getProductId())) {
                product.setQuantity(product.getQuantity() - productForTicket.getQuantity());
            }
        }
    }

    /**
     * Reescribe el archivo CSV para persistir los datos
     * @throws ApiException
     */
    private void writeFile() throws ApiException {
        try {
            FileWriter csvWriter = new FileWriter(loadFile("dbProductos.csv"));
            // Inicializo columnas
            csvWriter.append("productId");
            csvWriter.append(",");
            csvWriter.append("name");
            csvWriter.append(",");
            csvWriter.append("category");
            csvWriter.append(",");
            csvWriter.append("brand");
            csvWriter.append(",");
            csvWriter.append("price");
            csvWriter.append(",");
            csvWriter.append("quantity");
            csvWriter.append(",");
            csvWriter.append("freeShipping");
            csvWriter.append(",");
            csvWriter.append("prestige");
            csvWriter.append("\n");

            // Cargo producto por producto
            for (ProductDto product: getProducts()) {
                // Parseo prestigio
                StringBuilder prestige = new StringBuilder();
                for (int i = 0; i < product.getPrestige(); i++) {
                    prestige.append("*");
                }
                // Parseo precio
                String price = "$" + String.format("%,.2f", product.getPrice());
                price = price.replace(".00", "");
                price = price.replace(",", ".");
                csvWriter.append(product.getProductId().toString());
                csvWriter.append(",");
                csvWriter.append(product.getName());
                csvWriter.append(",");
                csvWriter.append(product.getCategory());
                csvWriter.append(",");
                csvWriter.append(product.getBrand());
                csvWriter.append(",");
                csvWriter.append(price);
                csvWriter.append(",");
                csvWriter.append(product.getQuantity().toString());
                csvWriter.append(",");
                csvWriter.append(product.getFreeShipping() ? "SI" : "NO");
                csvWriter.append(",");
                csvWriter.append(prestige);
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al leer el archivo.");
        }
    }

    /**
     * Lee el archivo y carga todos los productos de la base de datos
     */
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
            productDto.setProductId(Integer.parseInt(parameters.get(0)));
            productDto.setName(parameters.get(1));
            productDto.setCategory(parameters.get(2));
            productDto.setBrand(parameters.get(3));
            productDto.setPrice(Double.parseDouble(parameters.get(4).replace("$", "").replace(".", "")));
            productDto.setQuantity(Integer.parseInt(parameters.get(5)));
            productDto.setFreeShipping(parameters.get(6).equals("SI"));
            productDto.setPrestige(parameters.get(7).split("").length);
            getProducts().add(productDto);
        }
    }

    /**
     * Retorna un archivo dado el nombre
     * @param fileName nombre del archivo
     */
    public File loadFile(String fileName) {
        File file = new File("");
        try {
            file = ResourceUtils.getFile(file.getAbsolutePath()+ "/src/main/resources/" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

}