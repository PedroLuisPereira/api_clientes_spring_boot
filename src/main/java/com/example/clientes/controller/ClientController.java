package com.example.clientes.controller;


import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientCreateDto;
import com.example.clientes.dto.ClientDto;
import com.example.clientes.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

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
    public Client create(@Valid @RequestBody ClientCreateDto client) {
        return service.create(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable int id, @Valid @RequestBody ClientCreateDto client) {
        return service.update(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
