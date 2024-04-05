package com.example.clientes.controller;

import com.example.clientes.domain.Client;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("clientes/")
public class ClienteRestController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteRestController.class);

    private List<Client> clients = new ArrayList<>(Arrays.asList(
            new Client(1, "juan", "123", "Juan"),
            new Client(2, "ana", "123", "Ana"),
            new Client(3, "maria", "123", "Maria")
    ));

    @RequestMapping("/log/logapi")
    public String logMethod() {
        logger.info("Hello from Spring Boot Logging Application.");
        logger.info("This is sample info message");
        logger.warn("This is sample warn message");
        logger.error("This is sample error message");

        return "Hello Simplifying Tech";
    }

    @GetMapping()
    public ResponseEntity<List<Client>> getClientes() {
        return ResponseEntity.ok(clients);
    }

    @GetMapping("{userName}")
    public ResponseEntity<Client> getCliente(@PathVariable String userName) {

        if (userName.length() != 3) {
            throw new BadRequestException("Error en el usarName");
        }

        return clients.stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(userName))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));
    }

    @PostMapping()
    public ResponseEntity<Client> setCliente(@RequestBody Client client) {
        clients.add(client);

        //Otener URL de servicio
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(client.getEmail())
                .toUri();

        return ResponseEntity.created(location).body(client);
    }

    @PutMapping("{userName}")
    public ResponseEntity<Client> setCliente(@PathVariable String userName, @RequestBody Client client) {

        Client clientEncontrado = clients.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(userName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));

        return ResponseEntity.ok(clientEncontrado);
    }

    @DeleteMapping("{userName}")
    public ResponseEntity<Client> deleteCliente(@PathVariable String userName) {

        Client clientEncontrado = clients.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(userName))
                .findFirst()
                .orElseThrow();

        clients.remove(clientEncontrado);

        return ResponseEntity.noContent().build();
    }
}
