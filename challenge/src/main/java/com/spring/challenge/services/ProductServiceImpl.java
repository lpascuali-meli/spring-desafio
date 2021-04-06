package com.spring.challenge.services;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.TicketDto;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
            orderList(result, order);
        }
        if (result.size() == 0) { throw new ApiException(HttpStatus.BAD_REQUEST, "No se encontraron productos para los filtros aplicados.");}
        return result;
    }

    @Override
    public TicketDto createPurchaseRequest(List<ProductDto> products) throws ApiException {
        List<Integer> ids = products.stream().map(ProductDto::getId).collect(Collectors.toList());
        List<ProductDto> productToPurchase = productRepository.getProductsForPurchase(ids);
        if (products.size() != productToPurchase.size()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se encontraron los productos que desea comprar.");
        }
        TicketDto ticket = new TicketDto();
        for (ProductDto product: productToPurchase) {
            ticket.getArticles().add(product);
            ticket.setTotal(ticket.getTotal() + product.getPrice());
        }
        return ticket;
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
