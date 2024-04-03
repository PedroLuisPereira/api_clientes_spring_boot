package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientDto;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    private static final String REGISTRO_NO_ENCONTRADO = "Registro no econtrado";
    private static final String USUARIO_EXISTE = "Username ya existe";

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> listAll() {
        return repository.findAll();
    }

    @Override
    public ClientDto listById(int id) {

        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

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
                .orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));
    }

    @Override
    @Transactional
    public Client create(Client client) {


        List<Client> clients = repository.findByEmail(client.getEmail());

        if (!clients.isEmpty()) {
            throw new BadRequestException(USUARIO_EXISTE);
        }

        return repository.save(client);

    }

    @Override
    @Transactional
    public Client update(int id, Client client) {

        Client clientOld = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

        List<Client> clients = repository.findByEmail(client.getEmail());

        if (!clients.isEmpty() && client.getId() != clients.get(0).getId()) {
            throw new BadRequestException(USUARIO_EXISTE);
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