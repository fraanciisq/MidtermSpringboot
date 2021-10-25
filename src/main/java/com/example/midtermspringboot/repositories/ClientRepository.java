package com.example.midtermspringboot.repositories;

import com.example.midtermspringboot.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ClientRepository extends JpaRepository<Client, Long> {
}
