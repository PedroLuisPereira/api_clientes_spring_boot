package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository repository;

    @Override
    public List<Client> listAll() {
        return repository.findAll();
    }

    @Override
    public Client listById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));
    }

    @Override
    public Client listByUsername(String username) {
        return repository.findByUsername(username).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));
    }

    @Override
    @Transactional
    public Client create(Client client) {

        List<Client> clients = repository.findByUsername(client.getUsername());

        if (!clients.isEmpty()) {
            throw new BadRequestException("Username ya existe");
        }

        return repository.save(client);

    }

    @Override
    @Transactional
    public Client update(Client client) {

        Client clietOld = repository.findById(client.getId()).orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));

        List<Client> clients = repository.findByUsername(client.getUsername());

        if (!clients.isEmpty() && client.getId() != clients.stream().findFirst().get().getId()) {
            throw new BadRequestException("Username ya existe");
        }

        return repository.save(client);

    }

    @Override
    @Transactional
    public void delete(int id) {

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no econtrado"));

        repository.deleteById(id);

    }

}