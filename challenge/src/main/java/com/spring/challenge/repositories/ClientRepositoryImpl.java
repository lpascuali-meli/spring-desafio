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
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final List<ClientDto> clients;

    public ClientRepositoryImpl() {
        clients = new ArrayList<>();
        readClients();
    }

    /**
     * Crea un cliente, y lo persiste en la db.
     * @param clientDto Cliente a crear con todos sus atributos
     * @return Retorna el cliente creado
     * @throws ApiException
     */
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

    /**
     * Consulta un cliente por nombre
     * @param name Nombre del cliente a buscar
     * @return Si encuentra cliente lo retorna, sino retorna null
     */
    private ClientDto getClientByName(String name) {
        for (ClientDto client: clients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }


    /**
     * Consulta los clientes por provincia
     * @param filters Nombre de la provincia
     * @return Si encuentra clientes los retorna, sino retorna null
     */
    @Override
    public List<ClientDto> getClientsByFilters(Map<String, String> filters) {
        return clients.stream()
                .filter(client -> {
                    boolean matches = true;
                    if (filters.get("state") != null) {
                        matches = client.getState().equals(filters.get("state"));
                    }
                    return matches;
                }).collect(Collectors.toList());
    }


    /**
     * Lee el archivo que contiene a todos los clientes de la db.
     */
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

    /**
     * Reescribe el archivo dbCLients para persistir los datos
     * @throws ApiException
     */
    private void writeFile() throws ApiException {
        try {
            FileWriter csvWriter = new FileWriter(loadFile("dbClients.csv"));
            // Primero escribo nombre columnas
            csvWriter.append("name");
            csvWriter.append(",");
            csvWriter.append("state");
            csvWriter.append("\n");
            // Luego recorro cada cliente y agrego uno por fila
            for (ClientDto client: clients) {
                csvWriter.append(client.getName());
                csvWriter.append(",");
                csvWriter.append(client.getState());
                csvWriter.append("\n"); // Salto de línea
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al leer el archivo.");
        }
    }


    public File loadFile(String fileName) {
        File file = new File("");
        try {
            file = ResourceUtils.getFile( file.getAbsolutePath()+ "/src/main/resources/" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
}
