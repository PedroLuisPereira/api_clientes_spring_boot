package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.domain.Invoice;
import com.example.clientes.domain.Item;
import com.example.clientes.domain.Product;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.repository.ClientRepository;
import com.example.clientes.repository.InvoiceRepository;
import com.example.clientes.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;

    private static final String REGISTRO_NO_ENCONTRADO = "Registro no econtrado";

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ClientRepository clientRepository, ProductRepository productRepository  ){
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Invoice> listAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public InvoiceDto listById(int id) {
        Invoice factura = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

        return InvoiceDto.builder()
                .id(factura.getId())
                .description(factura.getDescription())
                .createAt(factura.getCreateAt())
                .total(factura.getTotal())
                .client(factura.getClient())
                .items(factura.getItems())
                .build();
    }

    @Override
    public Invoice create(InvoiceCreateDto invoice) {

        List<Item> listItems = new ArrayList<Item>();

        Client cliente = clientRepository.findById(invoice.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

        double total = invoice.getItems().stream().mapToDouble(item -> {
            Product producto = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

            Item itemNuevo = Item.builder()
                    .units(item.getUnits())
                    .product(producto)
                    .subTotal(item.getUnits() * producto.getPrice())
                    .build();

            listItems.add(itemNuevo);

            return item.getUnits() * producto.getPrice();
        }).sum();

        return invoiceRepository.save(
                Invoice.builder()
                        .description(invoice.getDescription())
                        .total(total)
                        .client(cliente)
                        .items(listItems)
                        .createAt(new Date())
                        .build());

    }


}