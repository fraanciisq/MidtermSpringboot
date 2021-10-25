package com.example.midtermspringboot.controllers;

import com.example.midtermspringboot.entities.Client;
import com.example.midtermspringboot.repositories.ClientRepository;
import com.example.midtermspringboot.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @GetMapping(value = "/clients/id")
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
                System.out.println("Client Already exists!");
                return Status.THE_USER_ALREADY_EXISTS;
            }
        }
        clientRepository.save(newClient);
        return Status.REGISTERED_SUCCESSFULLY;
    }

    @PostMapping("/clients/login")
    public Status loginClient(@Valid @RequestBody Client client) {
        List<Client> clients = clientRepository.findAll();
        for (Client other : clients) {
            if (other.equals(client)) {
                return Status.LOGIN_SUCCESSFULLY;
            }
        }        return Status.FAILED;
    }

    @PostMapping("/clients/logout")
    public Status logOutClient(@Valid @RequestBody Client client) {List<Client> clients = clientRepository.findAll();
        for (Client other : clients) {
            if (other.equals(client)) {
                return Status.LOGOUT_SUCCESSFULLY;
            }
        }        return Status.FAILED;
    }

    @PutMapping("/clients/client_id/update")
    public Client updatePost(@PathVariable Long client_id, @Valid @RequestBody Client clientRequest) {
        return clientRepository.findById(client_id).map(client -> {
            client.setEmail(clientRequest.getEmail());
            client.setEmail(clientRequest.getEmail());
            client.setUsername(clientRequest.getUsername());
            client.setPassword(clientRequest.getPassword());
            return clientRepository.save(client);
        }).orElseThrow(() -> new Exception("Client id " + client_id + " cannot be found"));
    }

    @DeleteMapping("/clients/client_id/delete")
    public Status deleteClient(@PathVariable("id") Long id) {
        boolean exists = clientRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("The client with the given id" + id + "does not exists");
        }
        clientRepository.deleteById(id);
        return Status.DELETED;
    }

    @DeleteMapping("/clients/delete/all")
    public Status deleteClients() {
        clientRepository.deleteAll();
        return Status.DELETED;
    }
}