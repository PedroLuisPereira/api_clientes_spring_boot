package com.example.clientes.service;

import com.example.clientes.domain.Client;
import java.util.List;

public interface ClientService {

    List<Client> listAll();

    Client listById(int id);

    Client listByUsername(String username);

    Client create(Client client);

    Client update(Client client);

    void delete(int id);


}