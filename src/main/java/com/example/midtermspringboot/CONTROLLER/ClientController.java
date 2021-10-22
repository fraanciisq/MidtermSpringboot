package com.example.midtermspringboot.CONTROLLER;

import com.example.midtermspringboot.ENTITY.Client;
import com.example.midtermspringboot.REPOSITORY.ClientRepository;
import com.example.midtermspringboot.STATUS.Status;
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
                System.out.println("Client Already exists!");
                return Status.USER_ALREADY_EXISTS;
            }
        }
        clientRepository.save(newClient);
        return Status.SUCCESSFULLY_REGISTERED;
    }

    @PostMapping("/clients/login")
    public Status loginClient(@Valid @RequestBody Client client) {
        List<Client> clients = clientRepository.findAll();
        for (Client other : clients) {
            if (other.equals(client)) {
                return Status.SUCCESSFULLY_LOGIN;
            }
        }        return Status.FAILURE;
    }

    @PostMapping("/clients/logout")
    public Status logOutClient(@Valid @RequestBody Client client) {
        List<Client> clients = clientRepository.findAll();
        for (Client other : clients) {
            if (other.equals(client)) {
                return Status.SUCCESSFULLY_LOGOUT;
            }
        }        return Status.FAILURE;
    }

    @PutMapping("/clients/{clientid}")
    public Client updatePost(@PathVariable Long clientid, @Valid @RequestBody Client clientRequest) {
        return clientRepository.findById(clientid).map(client -> {
            client.setEmail(clientRequest.getEmail());
            client.setEmail(clientRequest.getEmail());
            client.setUsername(clientRequest.getUsername());
            client.setPassword(clientRequest.getPassword());
            return clientRepository.save(client);
        }).orElseThrow(() -> new Exception("Client id " + clientid + " not found"));
    }

    @DeleteMapping("/clients/{clientid}")
    public Status deleteClient(@PathVariable("id") Long id) {
        boolean exists = clientRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("client with id" + id + "does not exists");
        }
        clientRepository.deleteById(id);
        return Status.SUCCESSFULLY_DELETED;
    }

    @DeleteMapping("/clients/deleteall")
    public Status deleteClients() {
        clientRepository.deleteAll();
        return Status.SUCCESSFULLY_DELETED;
    }
}