package com.example.midtermspringboot.controllers;

import com.example.midtermspringboot.entities.Client;
import com.example.midtermspringboot.repositories.ClientRepository;
import com.example.midtermspringboot.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients/all")
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @GetMapping(value = "/clients/{id}")
    public Optional<Client> getClient(@PathVariable("id") Long id) {
        return clientRepository.findById(id);
    }

    @PostMapping("/clients/register")
    public Status registerClient(@Valid @RequestBody Client newClient) {
        List<Client> clients = clientRepository.findAll();

        System.out.println("New client: " + newClient.toString());

        for (Client client : clients) {
            System.out.println("Registered client: " + newClient.toString());

            if (client.equals(newClient)) {
                System.out.println("This Specific Client Already exists!");
                return Status.THE_CLIENT_ALREADY_EXISTS;
            }
        }
        clientRepository.save(newClient);
        return Status.A_NEW_CLIENT_REGISTERED_SUCCESSFULLY;
    }

    @PostMapping("/clients/login")
    public Status loginClient(@Valid @RequestBody Client client) {
        List<Client> clients = clientRepository.findAll();
        for (Client other : clients) {
            if (other.equals(client)) {
                return Status.THE_CLIENT_LOGIN_SUCCESSFULLY;
            }
        }        return Status.FAILED;
    }

    @PostMapping("/clients/logout")
    public Status logOutClient(@Valid @RequestBody Client client) {List<Client> clients = clientRepository.findAll();
        for (Client other : clients) {
            if (other.equals(client)) {
                return Status.THE_CLIENT_LOGOUT_SUCCESSFULLY;
            }
        }        return Status.FAILED;
    }

    @PutMapping("/clients/{clientid}")
    public Client updatePost(@PathVariable Long clientid, @Valid @RequestBody Client clientRequest) {
        return clientRepository.findById(clientid).map(client -> {
            client.setEmail(clientRequest.getEmail());
            client.setEmail(clientRequest.getEmail());
            client.setUsername(clientRequest.getUsername());
            client.setPassword(clientRequest.getPassword());
            return clientRepository.save(client);
        }).orElseThrow(() -> new Exception("Client id " + clientid + " cannot be found"));
    }

    @DeleteMapping(value = "/clients/{clientid}")
    public Status deleteClient(@PathVariable("clientid") Long clientid) {
        boolean exists = clientRepository.existsById(clientid);
        if (!exists) {
            throw new IllegalStateException("client with the given id " + clientid + " does not exists");
        }
        clientRepository.deleteById(clientid);
        return Status.DELETED;
    }

    @DeleteMapping("/clients/deleteall")
    public Status deleteClients() {
        clientRepository.deleteAll();
        return Status.DELETED;
    }
}