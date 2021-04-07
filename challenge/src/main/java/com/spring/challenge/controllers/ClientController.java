package com.spring.challenge.controllers;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
