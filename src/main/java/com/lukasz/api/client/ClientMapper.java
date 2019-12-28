package com.lukasz.api.client;

import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toModel(ClientDto clientDto) {
        return new Client(clientDto.getName(), clientDto.getSurname(), clientDto.getEmail(), clientDto.getPassword());
    }

    public ClientDto toDto(Client client) {
        return new ClientDto(client.getClientId(), client.getName(), client.getSurname(), client.getEmail(), client.getPassword());
    }

}