package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientCreateDto;
import com.example.clientes.dto.ClientDto;
import java.util.List;

public interface ClientService {

    List<Client> listAll();

    ClientDto listById(int id);

    Client listByEmail(String email);

    Client create(ClientCreateDto client);

    Client update(int id, ClientCreateDto client);

    void delete(int id);


}