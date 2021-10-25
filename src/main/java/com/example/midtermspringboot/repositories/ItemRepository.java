package com.example.midtermspringboot.repositories;

import com.example.midtermspringboot.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByIdAndClientId(Long id, Long itemId);
    List<Item> findByClientId(Long id);
}