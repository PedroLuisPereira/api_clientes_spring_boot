package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientDto;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.net.ConnectException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository repository;

    @Override
    public List<Client> listAll() {
        return repository.findAll();
    }

    @Override
    public ClientDto listById(int id) {

        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro no econtrado"));

        return ClientDto.builder()
                .id(id)
                .address(client.getAddress())
                .email(client.getEmail())
                .name(client.getName())
                .invoices(client.getInvoices())
                .build();

    }

    @Override
    public Client listByEmail(String email) {
        return repository.findByEmail(email).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Registro no econtrado"));
    }

    @Override
    @Transactional
    public Client create(Client client) {



        List<Client> clients = repository.findByEmail(client.getEmail());

        if (!clients.isEmpty()) {
            throw new BadRequestException("Username ya existe");
        }

        return repository.save(client);

    }

    @Override
    @Transactional
    public Client update(int id, Client client) {

        Client clientOld = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro no econtrado"));

        List<Client> clients = repository.findByEmail(client.getEmail());

        if (!clients.isEmpty() && client.getId() != clients.stream().findFirst().get().getId()) {
            throw new BadRequestException("Username ya existe");
        }

        clientOld.setName(client.getName());
        clientOld.setEmail(client.getEmail());
        clientOld.setAddress(client.getAddress());

        return repository.save(clientOld);

    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

}