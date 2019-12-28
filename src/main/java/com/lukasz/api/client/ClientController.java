package com.lukasz.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clientDtos = clientService.getAllClients();
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }

    @GetMapping("/clients/{email}/{password}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable String email, @PathVariable String password) {
        //ClientDto clientDto = clientService.getClientById(clientId);
        ClientDto clientDto = clientService.getClientByIdAndPassword(email, password);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        ClientDto addedClientDto = clientService.addClient(clientDto);
        return new ResponseEntity<>(addedClientDto, HttpStatus.CREATED);
    }


    @PutMapping(value = "/clients/{clientId}")
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto, @PathVariable UUID clientId) {
        ClientDto updatedClientDto = clientService.updateClient(clientDto, clientId);
        return new ResponseEntity<>(updatedClientDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/clients/{clientId}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable UUID clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}