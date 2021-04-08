package com.spring.challenge.services;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;
import com.spring.challenge.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Valida el cliente a crear y si es correcto lo crea
     * @param clientDto
     * @return retorna el cliente creado
     * @throws ApiException
     */
    @Override
    public ClientDto createClient(ClientDto clientDto) throws ApiException {
        if (clientDto.getName() == null || clientDto.getState() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cliente con datos incompletos.");
        }
        return clientRepository.createClient(clientDto);
    }

    @Override
    public List<ClientDto> getClientsByFilters(Map<String, String> filters) throws ApiException {
        List<ClientDto> clientDtos = clientRepository.getClientsByFilters(filters);
        if (clientDtos.size() == 0) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No se encontraron clientes para la provinica indicada.");
        }
        return clientDtos;
    }
}
