package com.spring.challenge.controllers;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) throws ApiException {
        ClientDto clientCreated = clientService.createClient(clientDto);
        return new ResponseEntity<>(clientCreated, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ClientDto>> getProductsByFilters(@RequestParam Map<String, String> filters) throws ApiException {
        List<ClientDto> clients = clientService.getClientsByFilters(filters);
        return  new ResponseEntity<List<ClientDto>>(clients, HttpStatus.OK);
    }
}
