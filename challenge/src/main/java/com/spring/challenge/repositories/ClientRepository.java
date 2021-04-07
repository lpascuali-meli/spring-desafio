package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;

public interface ClientRepository {
    public ClientDto createClient(ClientDto clientDto) throws ApiException;
}
