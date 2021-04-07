package com.spring.challenge.services;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;

public interface ClientService {
    public ClientDto createClient(ClientDto clientDto) throws ApiException;
}
