package com.example.midtermspringboot.REPOSITORY;

import com.example.midtermspringboot.ENTITY.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ClientRepository extends JpaRepository<Client, Long> {
}
