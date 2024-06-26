package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.dto.ClientCreateDto;
import com.example.clientes.dto.ClientDto;
import com.example.clientes.exceptions.BadRequestException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    private static final String REGISTRO_NO_ENCONTRADO = "Registro no econtrado";

    private static final String CLIENT_EXISTE = "Cliente ya existe";

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    public void setup() {
        client = Client.builder()
                .id(1)
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();
    }

    @Test
    void listAll() {
        Client client2 = Client.builder()
                .id(2)
                .name("Ramesh2")
                .email("ramesh2@gmail.com")
                .address("Calle 20 #96-96")
                .build();

        // given
        when(clientRepository.findAll()).thenReturn((List.of(client, client2)));

        // when -  action or the behaviour that we are going test
        List<Client> clients = clientService.listAll();

        // then - verify the output
        Assertions.assertNotNull(clients);
        Assertions.assertEquals(2, clients.size());
    }

    @Test
    void listById() {
        // given
        Mockito.when(clientRepository.findById(1)).thenReturn((Optional.of(client)));

        // when -  action or the behaviour that we are going test
        ClientDto clientDto = clientService.listById(1);

        // then - verify the output
        Assertions.assertNotNull(clientDto);
        Assertions.assertEquals("ramesh@gmail.com", clientDto.getEmail());
    }

    @Test
    void listByEmail() {
        // given
        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(List.of(client));

        // when -  action or the behaviour that we are going test
        Client client = clientService.listByEmail("ramesh@gmail.com");

        // then - verify the output
        Assertions.assertNotNull(client);
        Assertions.assertEquals("ramesh@gmail.com", client.getEmail());
    }

    @Test
    void create() {

        // given - precondition or setup
        ClientCreateDto clientCreateDto = ClientCreateDto.builder()
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();
        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(new ArrayList<>());
        Mockito.when(clientRepository.save(any())).thenReturn(client);

        // when -  action or the behaviour that we are going test
        Client clientCreate = clientService.create(clientCreateDto);

        // then - verify the output
        Assertions.assertNotNull(clientCreate);
        Assertions.assertEquals("ramesh@gmail.com", clientCreate.getEmail());
    }

    @Test
    void createClienteExiste() {

        // given - precondition or setup
        ClientCreateDto clientCreateDto = ClientCreateDto.builder()
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();
        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(Collections.singletonList(client));

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () ->
                clientService.create(clientCreateDto)
        );

        // then
        Assertions.assertEquals(CLIENT_EXISTE, thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void update() {

        // given - precondition or setup
        ClientCreateDto clientCreateDto = ClientCreateDto.builder()
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();

        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(new ArrayList<>());
        Mockito.when(clientRepository.save(any())).thenReturn(client);

        // when -  action or the behaviour that we are going test
        Client clientUpdate = clientService.update(1, clientCreateDto);

        // then - verify the output
        Assertions.assertNotNull(clientUpdate);
        Assertions.assertEquals("ramesh@gmail.com", clientUpdate.getEmail());
    }

    @Test
    void updateClienteNoExiste() {

        // given - precondition or setup
        ClientCreateDto clientCreateDto = ClientCreateDto.builder()
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();

        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                clientService.update(1, clientCreateDto)
        );

        // then
        Assertions.assertEquals(REGISTRO_NO_ENCONTRADO, thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findById(anyInt());
    }

    @Test
    void updateEmailYaExiste() {

        // given - precondition or setup
        ClientCreateDto clientCreateDto = ClientCreateDto.builder()
                .name("Ramesh")
                .email("ramesh@gmail.com")
                .address("Calle 20 #96-96")
                .build();

        Client client2 = Client.builder()
                .id(2)
                .name("Ramesh2")
                .email("ramesh2@gmail.com")
                .address("Calle 20 #96-96")
                .build();

        Mockito.when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(Collections.singletonList(client2));

        // when -  action or the behaviour that we are going test
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () ->
                clientService.update(1, clientCreateDto)
        );

        // then
        Assertions.assertEquals(CLIENT_EXISTE, thrown.getMessage());
        Mockito.verify(clientRepository, times(1)).findById(anyInt());
        Mockito.verify(clientRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void destroy() {

        //given
        Mockito.doNothing().when(clientRepository).deleteById(anyInt());

        //when
        clientService.destroy(1);

        //then
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(anyInt());
    }
}