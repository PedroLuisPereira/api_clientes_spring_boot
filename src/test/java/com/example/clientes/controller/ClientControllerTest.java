package com.example.clientes.controller;

import com.example.clientes.domain.Client;
import com.example.clientes.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        clientRepository.deleteAll();
    }

    @Test
    void listAll() throws Exception {
        // given - precondition or setup
        List<Client> clients = new ArrayList<>();
        clients.add(Client.builder().name("Juan").email("juan@gmail.com").address("Calle 1").build());
        clients.add(Client.builder().name("Ana").email("ana@gmail.com").address("Calle 1").build());
        clientRepository.saveAll(clients);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/clients"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Juan")))
                .andExpect(jsonPath("$.size()", is(clients.size())));
    }

    @Test
    void listById() throws Exception {
        // given - precondition or setup
        Client cliente = Client.builder().id(1).name("Juan").email("juan@gmail.com").address("Calle 1").build();

        clientRepository.save(cliente);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/clients/{id}", cliente.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(cliente.getName())))
                .andExpect(jsonPath("$.address", is(cliente.getAddress())))
                .andExpect(jsonPath("$.email", is(cliente.getEmail())));
    }

    @Test
    void create() throws Exception {

        // given - precondition or setup
        Client cliente = Client.builder().id(1).name("Juan").email("juan@gmail.com").address("Calle 1").build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(cliente.getName())))
                .andExpect(jsonPath("$.address", is(cliente.getAddress())))
                .andExpect(jsonPath("$.email", is(cliente.getEmail())));

    }

    @Test
    void update() throws Exception {
        // given - precondition or setup
        Client cliente = Client.builder().id(1).name("Juan").email("juan@gmail.com").address("Calle 1").build();

        clientRepository.save(cliente);

        Client cliente2 = Client.builder().id(1).name("Juan David").email("juan@gmail.com").address("Calle 1").build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/clients/{id}", cliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente2)));


        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(cliente2.getName())))
                .andExpect(jsonPath("$.address", is(cliente2.getAddress())))
                .andExpect(jsonPath("$.email", is(cliente2.getEmail())));
    }

    @Test
    void deleteCliente() throws Exception{
        // given - precondition or setup
        Client cliente = Client.builder().id(1).name("Juan").email("juan@gmail.com").address("Calle 1").build();
        clientRepository.save(cliente);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/clients/{id}", cliente.getId()));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}