package com.example.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ItemService {

    @Autowired
    ObjectMapper objectMapper;

    UUID uuid = UUID.randomUUID();
    UUID uuid2 = UUID.randomUUID();
    UUID uuid3 = UUID.randomUUID();

    Item firstItem = new Item(uuid, "first item");
    Item secondItem = new Item(uuid2, "second item");
    Item thirdItem = new Item(uuid3, "third item");

    LinkedList<Item> items = new LinkedList<Item>();

    @GetMapping("/items")
    public ResponseEntity getItems() throws JsonProcessingException {

        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);

        return ResponseEntity.ok(objectMapper.writeValueAsString(items));
    }

    @PostMapping("/items")
    public ResponseEntity setItems(@RequestHeader("name") String name) throws JsonProcessingException {

        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);

        for (Item x : items ) {
            String itemName = x.getName();
            if (itemName.equals(name)){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        UUID generatedGUID = UUID.randomUUID();
        Item newItem = new Item(generatedGUID, name);
        items.add(newItem);
        return ResponseEntity.ok(objectMapper.writeValueAsString(items))/*status(HttpStatus.OK).build()*/;
    }
}
