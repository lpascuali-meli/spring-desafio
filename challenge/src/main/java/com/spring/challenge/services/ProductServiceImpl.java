package com.spring.challenge.services;

import com.spring.challenge.dtos.*;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private static AtomicLong atomicLong;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        atomicLong = new AtomicLong(1);
    }

    @Override
    public List<ProductDto> getProductsByFilters(Map<String, String> filters) throws ApiException {
        Integer order = filters.get("order") != null ? Integer.parseInt(filters.get("order")) : null;
        filters.remove("order");
        if (filters.size() > 2) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No puede ingresar más de dos filtros.");
        }
        List<ProductDto> result;
        result = productRepository.getProductsByFilters(filters);
        if (order != null) {
            if (order < 0 || order > 2) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "No existe el método de ordenamiento elegido.");
            }
            orderList(result, order);
        }
        if (result.size() == 0) { throw new ApiException(HttpStatus.BAD_REQUEST, "No se encontraron productos para los filtros aplicados.");}
        return result;
    }

    @Override
    public TicketResponse createPurchaseRequest(OrderForTicketDto orderForTicket) throws ApiException {
        // Tomo los articulos de la orden del cliente
        List<ProductForTicketDto> products = orderForTicket.getArticles();
        // Obtengo los ids de los productos
        List<Integer> ids = products.stream().map(ProductForTicketDto::getProductId).collect(Collectors.toList());
        // Consulto los precios y cantidades disponibles de cada uno
        HashMap<Integer, ProductDto> productsToPurchase = productRepository.getProductsForPurchase(ids);
        if (products.size() == 0) {
            // Validación por lista de compras vacía
            throw new ApiException(HttpStatus.BAD_REQUEST, "Debe indicar los artículos que desea comprar.");
        }
        if (productsToPurchase.size() == 0) {
            // Validación por productos no encontrados
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se encontraron los productos que desea comprar.");
        }
        // Creo el ticket nuevo
        TicketDto ticket = new TicketDto();
        ticket.setArticles(new ArrayList<ProductForTicketDto>());
        // Recorro los productos de la orden
        for (ProductForTicketDto product: products) {
            // Los valido
            validateArticle(product, productsToPurchase);
            // Si fue valido los agrego
            ticket.getArticles().add(product);
        }
        // Creamos ID para el ticket
        ticket.setId(atomicLong.getAndAdd(1));
        // Creamos ticket acumulado
        TicketDto accumulatedTicket = productRepository.createTicket(ticket);
        // Consultamos nuevamente precios (nuevos y viejos)
        ids = accumulatedTicket.getArticles().stream().map(ProductForTicketDto::getProductId).collect(Collectors.toList());
        productsToPurchase = productRepository.getProductsForPurchase(ids);
        for (ProductForTicketDto product: accumulatedTicket.getArticles()) {
            double price = productsToPurchase.get(product.getProductId()).getPrice();
            accumulatedTicket.setTotal(accumulatedTicket.getTotal() + (price * product.getQuantity()));
        }
        StatusCodeDto statusCodeDto = new StatusCodeDto(200, "La solicitud de compra se completó con éxito.");
        return new TicketResponse(accumulatedTicket, statusCodeDto);
    }

    private void validateArticle(ProductForTicketDto product, HashMap<Integer, ProductDto> productsToPurchase) throws ApiException {
        if (product.getQuantity() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Debe especificar la cantidad del artículo "
                    + product.getName());
        }
        ProductDto productToCompare = productsToPurchase.get(product.getProductId());
        if (productToCompare == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se encontró el producto " + product.getName());
        }
        if (!product.getBrand().equals(productToCompare.getBrand())
            || !product.getName().equals(productToCompare.getName())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La descripción del producto " + product.getName()
                + " no coincide con la base de datos.");
        }
        if (product.getQuantity() > productToCompare.getQuantity()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La tienda no posee stock suficiente para la cantidad de "
                    + product.getName() + " solicitadas.");
        }
    }

    private void orderList(List<ProductDto> result, Integer order) {
        Comparator<ProductDto> comparator;
        switch (order) {
            case 0:
                comparator = ascAlphabetic();
                break;
            case 1:
                comparator = descAlphabetic();
                break;
            case 2:
                comparator = descPrice();
                break;
            case 3:
                comparator = ascPrice();
                break;
            default:
                comparator = null;
        }
        result.sort(comparator);
    }

    private Comparator<ProductDto> ascAlphabetic() {
        return new Comparator<ProductDto>() {
            @Override
            public int compare(ProductDto p1, ProductDto p2) {
                return p1.getName().compareTo(p2.getName());
            }
        };
    }
    private Comparator<ProductDto> descAlphabetic() {
        return new Comparator<ProductDto>() {
            @Override
            public int compare(ProductDto p1, ProductDto p2) {
                return -1 * p1.getName().compareTo(p2.getName());
            }
        };
    }
    private Comparator<ProductDto> descPrice() {
        return new Comparator<ProductDto>() {
            @Override
            public int compare(ProductDto p1, ProductDto p2) {
                return Double.compare(p2.getPrice(), p1.getPrice());
            }
        };
    }
    private Comparator<ProductDto> ascPrice() {
        return new Comparator<ProductDto>() {
            @Override
            public int compare(ProductDto p1, ProductDto p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        };
    }
}
