package com.example.clientes.controller;


import com.example.clientes.domain.Client;
import com.example.clientes.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public List<Client> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public Client listById(@PathVariable int id) {
        return service.listById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Client create(@RequestBody Client client) {
        return service.create(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable int id, @RequestBody Client client) {
        client.setId(id);
        return service.update(client);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
