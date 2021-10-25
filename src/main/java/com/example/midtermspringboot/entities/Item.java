package com.example.midtermspringboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name_of_item;
    @NotBlank
    private String type_of_item;
    @NotBlank
    private int price;

    public Item() {
    }
    public Item(@NotBlank String name_of_item,
                @NotBlank String type_of_item,
                @NotBlank int price) {

        this.name_of_item = name_of_item;
        this.type_of_item = type_of_item;
        this.price = price;
    }

    public Item(@NotBlank String name_of_item,
                @NotBlank String type_of_item,
                @NotBlank Integer price) {

        this.name_of_item = name_of_item;
        this.type_of_item = type_of_item;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_of_item() {
        return name_of_item;
    }

    public void setName_of_item(String name_of_item) {
        this.name_of_item = name_of_item;
    }

    public String getType_of_item() {
        return type_of_item;
    }

    public void setType_of_item(String type_of_item) {
        this.type_of_item = type_of_item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item= (Item) o;
        return Objects.equals(name_of_item, item.name_of_item) && Objects.equals(type_of_item, item.type_of_item) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {return Objects.hash(id, name_of_item, type_of_item, price);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name_of_item='" + name_of_item + '\'' +
                ", category='" + type_of_item + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientid", nullable = false)
    private Client client;

}