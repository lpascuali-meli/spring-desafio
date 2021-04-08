package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;

import java.util.List;
import java.util.Map;

public interface ClientRepository {
    public ClientDto createClient(ClientDto clientDto) throws ApiException;

    List<ClientDto> getClientsByFilters(Map<String, String> filters);
}
