package com.spring.challenge.controllers;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.TicketDto;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ProductDto>> getProductsByFilters(@RequestParam Map<String, String> reqParam) throws ApiException {
        List<ProductDto> products = productService.getProductsByFilters(reqParam);
        return  new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }

    @PostMapping("purchase-request")
    public ResponseEntity<TicketDto> createPurchaseRequest(@RequestBody List<ProductDto> products) {
        TicketDto ticket = productService.createPurchaseRequest(products);
        return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);

    }
}
