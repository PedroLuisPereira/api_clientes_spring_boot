package com.example.clientes.controller;

import com.example.clientes.domain.Client;
import com.example.clientes.domain.Invoice;
import com.example.clientes.domain.Item;
import com.example.clientes.domain.Product;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.ItemCreateDto;
import com.example.clientes.repository.ClientRepository;
import com.example.clientes.repository.InvoiceRepository;
import com.example.clientes.repository.ItemRepository;
import com.example.clientes.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();
        productRepository.deleteAll();
        invoiceRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void listAll() throws Exception {

        // given - precondition or setup
        Client cliente = clientRepository.save(Client.builder().name("Juan").email("juan@gmail.com").address("Calle 1").build());

        Product product1 = productRepository.save(Product.builder().price(5000.0).name("Carro").build());
        Product product2 = productRepository.save(Product.builder().price(6000.0).name("Regla").build());

        Invoice invoice = invoiceRepository.save(Invoice.builder()
                .description("Una descripción")
                .total(2200.0)
                .client(cliente)
                .items(List.of(
                        Item.builder().units(2).subTotal(1000.0).product(product1).build(),
                        Item.builder().units(2).subTotal(1200.0).product(product2).build()
                ))
                //.items(new ArrayList<>())
                .createAt(new Date())
                .build());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/invoices"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].description", is(invoice.getDescription())))
                .andExpect(jsonPath("$[0].total", is(invoice.getTotal())));
    }

    @Test
    void listById() throws Exception {
        // given - precondition or setup
        Client cliente = clientRepository.save(Client.builder().name("Juan").email("juan@gmail.com").address("Calle 1").build());

        Product product1 = productRepository.save(Product.builder().price(5000.0).name("Carro").build());
        Product product2 = productRepository.save(Product.builder().id(2L).price(6000.0).name("Regla").build());

        Invoice invoice = invoiceRepository.save(Invoice.builder()
                .description("Una descripción")
                .total(2200.0)
                .client(cliente)
                .items(List.of(
                        Item.builder().units(2).subTotal(1000.0).product(product1).build(),
                        Item.builder().units(2).subTotal(1200.0).product(product2).build()
                ))
                //.items(new ArrayList<>())
                .createAt(new Date())
                .build());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/invoices/{id}", invoice.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.description", is(invoice.getDescription())))
                .andExpect(jsonPath("$.total", is(invoice.getTotal())))
                .andExpect(jsonPath("$.client.name", is(invoice.getClient().getName())))

        ;
    }

    @Test
    void create() throws Exception {

        // given - precondition or setup
        Client cliente = clientRepository.save(Client.builder().name("Juan").email("juan@gmail.com").address("Calle 1").build());

        Product product1 = productRepository.save(Product.builder().price(5000.0).name("Carro").build());
        Product product2 = productRepository.save(Product.builder().price(6000.0).name("Regla").build());

        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(cliente.getId())
                .description("Una descripción")
                .items(List.of(
                        ItemCreateDto.builder().units(2).productId(product1.getId().intValue()).build(),
                        ItemCreateDto.builder().units(2).productId(product2.getId().intValue()).build()))
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceCreateDto)));

        // then - verify the output
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.description", is(invoiceCreateDto.getDescription())))
                .andExpect(jsonPath("$.total", is((2 * product1.getPrice()) + 2 * product2.getPrice())));
    }
}