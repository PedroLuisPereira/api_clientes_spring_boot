package com.example.clientes.service;

import com.example.clientes.domain.Client;
import com.example.clientes.domain.Invoice;
import com.example.clientes.domain.Item;
import com.example.clientes.domain.Product;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;
import com.example.clientes.exceptions.BadRequestException;
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

        List<Item> listItems = new ArrayList<>();

        if(invoice.getItems().isEmpty()){
            throw new BadRequestException("Deben existir item");
        }

        //validar si existe el cliente
        Client cliente = clientRepository.findById(invoice.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

        //validar cada item
        double total = invoice.getItems().stream().mapToDouble(item -> {

            if(item.getUnits() <= 0 ){
                throw new BadRequestException("El valor de units debe ser mayor que cero");
            }

            if(item.getProductId() <= 0 ){
                throw new BadRequestException("El valor de productId debe ser mayor que cero");
            }

            //buscar cada producto
            Product producto = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(REGISTRO_NO_ENCONTRADO));

            //crear un item con el producto
            Item itemNuevo = Item.builder()
                    .units(item.getUnits())
                    .product(producto)
                    .subTotal(item.getUnits() * producto.getPrice())
                    .build();

            //agragar cada item
            listItems.add(itemNuevo);

            //retornar el total del item
            return item.getUnits() * producto.getPrice();
        }).sum(); // sumar todos los item

        //guardar cada factura
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