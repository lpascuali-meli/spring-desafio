package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ClientDto;
import com.spring.challenge.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final List<ClientDto> clients;

    public ClientRepositoryImpl() {
        clients = new ArrayList<>();
        readClients();
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) throws ApiException {
        ClientDto clientDto1 = getClientByName(clientDto.getName());
        if (clientDto1 != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Ya existe un cliente con ese nombre.");
        }
        clients.add(clientDto);
        writeFile();
        return clientDto;
    }

    private ClientDto getClientByName(String name) {
        for (ClientDto client: clients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    private void readClients() {
        String COMMA_DELIMITER = ",";
        List<List<String>> result = null;
        try {
            result = Files.readAllLines(Paths.get(loadFile("dbClients.csv").getAbsolutePath()))
                    .stream()
                    .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < Objects.requireNonNull(result).size(); i++) {
            List<String> parameters = result.get(i);
            ClientDto clientDto = new ClientDto();
            clientDto.setName(parameters.get(0));
            clientDto.setState(parameters.get(1));
            clients.add(clientDto);
        }
    }

    private void writeFile() throws ApiException {
        try {
            FileWriter csvWriter = new FileWriter(loadFile("dbClients.csv"));
            csvWriter.append("name");
            csvWriter.append(",");
            csvWriter.append("state");
            csvWriter.append("\n");
            for (ClientDto client: clients) {
                csvWriter.append(client.getName());
                csvWriter.append(",");
                csvWriter.append(client.getState());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al leer el archivo.");
        }
    }


    public File loadFile(String fileName) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
}
