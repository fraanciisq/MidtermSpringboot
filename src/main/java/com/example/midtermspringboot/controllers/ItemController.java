package com.example.midtermspringboot.controllers;

import com.example.midtermspringboot.entities.Item;
import com.example.midtermspringboot.repositories.ClientRepository;
import com.example.midtermspringboot.repositories.ItemRepository;
import com.example.midtermspringboot.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/clients/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/clients/id")
    public List<Item> getAllCommentsByPostId(@PathVariable(value = "id") Long id) {
        return itemRepository.findByClientId(id);
    }

    @PostMapping("/clients/items/client_id")
    public Item createItem(@PathVariable (value = "client_id") Long client_id,
                           @Valid @RequestBody Item item) {
        return clientRepository.findById(client_id).map(client -> {
            item.setClient(client);
            return itemRepository.save(item);
        }).orElseThrow(() -> new Exception("Client id " + client_id + " cannot be found"));
    }

    @PutMapping("/clients/items/client_id/item_id")
    public Item updateItem(@PathVariable (value = "customer_id") Long client_id,
                           @PathVariable (value = "item_id") Long item_id,
                           @Valid @RequestBody Item itemRequest) {
        if(!clientRepository.existsById(client_id)) {
            throw new Exception("Item id " + client_id + " cannot be found");
        }

        return itemRepository.findById(item_id).map(item -> {
            item.setName_of_item(itemRequest.getName_of_item());
            item.setType_of_item(itemRequest.getType_of_item());
            item.setPrice(itemRequest.getPrice());
            return itemRepository.save(item);
        }).orElseThrow(() -> new Exception("Client id " + item_id + "cannot be found"));
    }


    @DeleteMapping(value = "/clients/items/item_id/client_id")
    public Status deleteItem(@PathVariable("item_id") Long item_id) {
        boolean exists = itemRepository.existsById(item_id);
        if (!exists) {
            throw new IllegalStateException("item with the given id " + item_id + " does not exists");
        }
        itemRepository.deleteById(item_id);
        return Status.DELETED;
    }

    @DeleteMapping("/clients/items/delete/all")
    public Status deleteItems() {
        clientRepository.deleteAll();
        return Status.DELETED;
    }
}
