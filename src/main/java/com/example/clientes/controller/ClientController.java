package com.example.clientes.controller;


import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientDto;
import com.example.clientes.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public List<Client> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ClientDto listById(@PathVariable int id) {
        return service.listById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@Valid @RequestBody Client client) {
        return service.create(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable int id, @RequestBody Client client) {
        return service.update(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
