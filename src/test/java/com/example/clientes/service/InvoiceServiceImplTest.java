package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.domain.Invoice;
import com.example.clientes.domain.Product;
import com.example.clientes.dto.ClientDto;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;
import com.example.clientes.dto.ItemCreateDto;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.ClientRepository;
import com.example.clientes.repository.InvoiceRepository;
import com.example.clientes.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private static final String PRODUCTO_NO_ENCONTRADO = "Producto no econtrado";

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no econtrado";

    private Client client;

    private Product product;

    private Invoice invoice;

    @BeforeEach
    public void setup() {
        client = Client.builder()
                .id(1)
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();

        product = Product.builder().price(5000.0).name("Carro").build();
        invoice = Invoice.builder().id(1L).total(2000.0).description("des").client(client).build();
    }

    @Test
    void listAll() {

        Invoice invoice2 = Invoice.builder().id(2L).total(3000.0).description("des 2").build();

        // given
        Mockito.when(invoiceRepository.findAll()).thenReturn((List.of(invoice, invoice2)));

        // when -  action or the behaviour that we are going test
        List<Invoice> invoices = invoiceService.listAll();

        // then - verify the output
        Assertions.assertNotNull(invoices);
        Assertions.assertEquals(2, invoices.size());
    }

    @Test
    void listById() {

        // given
        Mockito.when(invoiceRepository.findById(anyInt())).thenReturn(Optional.of(invoice));

        // when -  action or the behaviour that we are going test
        InvoiceDto invoiceDto = invoiceService.listById(1);

        // then - verify the output
        Assertions.assertNotNull(invoiceDto);
        Assertions.assertEquals("ramesh@gmail.com", invoiceDto.getClient().getEmail());
    }

    @Test
    void create() {

        // given - precondition or setup
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        Mockito.when(invoiceRepository.save(any())).thenReturn(invoice);


        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(1)
                .description("Una descripción")
                .items(List.of(
                        ItemCreateDto.builder().units(2).productId(1).build(),
                        ItemCreateDto.builder().units(2).productId(2).build()))
                .build();

        // when -  action or the behaviour that we are going test
        Invoice invoice = invoiceService.create(invoiceCreateDto);

        // then - verify the output
        Assertions.assertNotNull(invoice);
        Assertions.assertEquals(2000.0, invoice.getTotal());
    }

    @Test
    void createNoTieneItems() {

        // given - precondition or setup
        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(1)
                .description("Una descripción")
                .items(new ArrayList<>())
                .build();

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () ->
                invoiceService.create(invoiceCreateDto)
        );

        // then
        Assertions.assertEquals("Deben existir item", thrown.getMessage());
    }

    @Test
    void createClienteNoExiste() {

        // given - precondition or setup
        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(1)
                .description("Una descripción")
                .items(List.of(
                        ItemCreateDto.builder().units(2).productId(1).build(),
                        ItemCreateDto.builder().units(2).productId(2).build()))
                .build();
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                invoiceService.create(invoiceCreateDto)
        );

        // then
        Assertions.assertEquals(CLIENTE_NO_ENCONTRADO, thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findById(anyInt());
    }

    @Test
    void createUnitsMayorQueCero() {

        // given - precondition or setup
        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(1)
                .description("Una descripción")
                .items(List.of(
                        ItemCreateDto.builder().units(0).productId(1).build(),
                        ItemCreateDto.builder().units(0).productId(2).build()))
                .build();
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () ->
                invoiceService.create(invoiceCreateDto)
        );

        // then
        Assertions.assertEquals("El valor de units debe ser mayor que cero", thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findById(anyInt());
    }

    @Test
    void createProductIdMayorQueCero() {

        // given - precondition or setup
        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(1)
                .description("Una descripción")
                .items(List.of(
                        ItemCreateDto.builder().units(2).productId(0).build(),
                        ItemCreateDto.builder().units(3).productId(1).build()))
                .build();
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () ->
                invoiceService.create(invoiceCreateDto)
        );

        // then
        Assertions.assertEquals("El valor de productId debe ser mayor que cero", thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findById(anyInt());
    }

    @Test
    void createProductIdNoExiste() {

        // given - precondition or setup
        InvoiceCreateDto invoiceCreateDto = InvoiceCreateDto.builder()
                .clientId(1)
                .description("Una descripción")
                .items(List.of(
                        ItemCreateDto.builder().units(2).productId(1).build(),
                        ItemCreateDto.builder().units(3).productId(1).build()))
                .build();
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                invoiceService.create(invoiceCreateDto)
        );

        // then
        Assertions.assertEquals(PRODUCTO_NO_ENCONTRADO, thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findById(anyInt());
        Mockito.verify(productRepository, times(1)).findById(anyInt());
    }


}