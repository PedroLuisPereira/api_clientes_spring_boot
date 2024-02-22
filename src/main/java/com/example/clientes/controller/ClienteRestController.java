package com.example.clientes.controller;

import com.example.clientes.domain.Cliente;
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

    private List<Cliente> clientes = new ArrayList<>(Arrays.asList(
            new Cliente("juan", "123", "Juan"),
            new Cliente("ana", "123", "Ana"),
            new Cliente("maria", "123", "Maria")
    ));


    @GetMapping()
    public ResponseEntity<?> getClientes() {
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getCliente(@PathVariable String userName) {

        if (userName.length() != 3){
            throw new BadRequestException("Error en el usarName");
        }

        return ResponseEntity.ok(clientes.stream()
                .filter(cliente -> cliente.getUsername().equalsIgnoreCase(userName))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado")));

        //return ResponseEntity.noContent().build();
        //throw new ResourceNotFoundException("Cliente no econtrado");
    }

    @PostMapping()
    public ResponseEntity<?> setCliente(@RequestBody Cliente cliente) {
        clientes.add(cliente);

        //Otener URL de servicio
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(cliente.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(cliente);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> setCliente(@PathVariable String userName, @RequestBody Cliente cliente) {

        Cliente clienteEncontrado = clientes.stream()
                .filter(c -> c.getUsername().equalsIgnoreCase(userName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));


        clienteEncontrado.setPassword(cliente.getPassword());
        clienteEncontrado.setNombre(cliente.getNombre());

        return ResponseEntity.ok(clienteEncontrado);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteCliente(@PathVariable String userName) {

        Cliente clienteEncontrado = clientes.stream()
                .filter(c -> c.getUsername().equalsIgnoreCase(userName))
                .findFirst()
                .orElseThrow();

        clientes.remove(clienteEncontrado);

        return ResponseEntity.noContent().build();
    }
}
