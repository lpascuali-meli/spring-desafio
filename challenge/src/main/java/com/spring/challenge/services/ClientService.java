package com.spring.challenge.services;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;

import java.util.List;
import java.util.Map;

public interface ClientService {
    public ClientDto createClient(ClientDto clientDto) throws ApiException;

    List<ClientDto> getClientsByFilters(Map<String, String> filters) throws ApiException;
}
