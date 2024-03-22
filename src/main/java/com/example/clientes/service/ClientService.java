package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ClientService {

    List<Client> listAll();

    ClientDto listById(int id);

    Client listByEmail(String email);

    Client create(Client client);

    Client update(int id, Client client);

    void delete(int id);


}