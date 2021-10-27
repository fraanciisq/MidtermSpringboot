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


    @GetMapping("/clients/all/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/clients/{id}/items")
    public List<Item> getAllCommentsByPostId(@PathVariable(value = "id") Long id) {
        return itemRepository.findByClientId(id);
    }

    @PostMapping("/clients/{clientid}/items")
    public Item createItem(@PathVariable (value = "clientid") Long clientid,
                           @Valid @RequestBody Item item) {
        return clientRepository.findById(clientid).map(client -> {
            item.setClient(client);
            return itemRepository.save(item);
        }).orElseThrow(() -> new Exception("Client id " + clientid + " cannot be found"));
    }

    @PutMapping("/clients/{clientid}/items/{itemid}")
    public Item updateItem(@PathVariable (value = "clientid") Long clientid,
                           @PathVariable (value = "itemid") Long itemid,
                           @Valid @RequestBody Item itemRequest) {
        if(!clientRepository.existsById(clientid)) {
            throw new Exception("Item id " + clientid + " cannot be found");
        }

        return itemRepository.findById(itemid).map(item -> {
            item.setName_of_item(itemRequest.getName_of_item());
            item.setType_of_item(itemRequest.getType_of_item());
            item.setPrice(itemRequest.getPrice());
            return itemRepository.save(item);
        }).orElseThrow(() -> new Exception("Client id " + itemid + "cannot be found"));
    }


    @DeleteMapping(value = "/clients/items/{clientid}/items/{itemid}")
    public Status deleteItem(@PathVariable("itemid") Long itemid) {
        boolean exists = itemRepository.existsById(itemid);
        if (!exists) {
            throw new IllegalStateException("item with the given id " + itemid + " does not exists");
        }
        itemRepository.deleteById(itemid);
        return Status.DELETED;
    }

    @DeleteMapping("/clients/all/items/all")
    public Status deleteItems() {
        clientRepository.deleteAll();
        return Status.DELETED;
    }
}
