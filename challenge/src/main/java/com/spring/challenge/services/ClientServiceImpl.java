package com.spring.challenge.services;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) throws ApiException {
        if (clientDto.getName() == null || clientDto.getState() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cliente con datos incompletos.");
        }
        return clientRepository.createClient(clientDto);
    }
}
