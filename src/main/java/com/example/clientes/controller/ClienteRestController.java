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

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    private List<Client> clients = new ArrayList<>(Arrays.asList(
            new Client(1,"juan", "123", "Juan"),
            new Client(2,"ana", "123", "Ana"),
            new Client(3,"maria", "123", "Maria")
    ));


    @GetMapping()
    public ResponseEntity<?> getClientes() {
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getCliente(@PathVariable String userName) {

        if (userName.length() != 3){
            throw new BadRequestException("Error en el usarName");
        }

        return ResponseEntity.ok(clients.stream()
                .filter(client -> client.getUsername().equalsIgnoreCase(userName))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado")));

        //return ResponseEntity.noContent().build();
        //throw new ResourceNotFoundException("Cliente no econtrado");
    }

    @PostMapping()
    public ResponseEntity<?> setCliente(@RequestBody Client client) {
        clients.add(client);

        //Otener URL de servicio
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(client.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(client);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> setCliente(@PathVariable String userName, @RequestBody Client client) {

        Client clientEncontrado = clients.stream()
                .filter(c -> c.getUsername().equalsIgnoreCase(userName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));


        clientEncontrado.setPassword(client.getPassword());
        clientEncontrado.setNombre(client.getNombre());

        return ResponseEntity.ok(clientEncontrado);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteCliente(@PathVariable String userName) {

        Client clientEncontrado = clients.stream()
                .filter(c -> c.getUsername().equalsIgnoreCase(userName))
                .findFirst()
                .orElseThrow();

        clients.remove(clientEncontrado);

        return ResponseEntity.noContent().build();
    }
}
