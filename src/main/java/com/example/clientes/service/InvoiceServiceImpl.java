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

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Invoice> listAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public InvoiceDto listById(int id) {
        Invoice factura = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no econtrado"));

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
    public Invoice listByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listByEmail'");
    }

    @Override
    public Invoice create(InvoiceCreateDto invoice) {

        List<Item> listItems = new ArrayList<Item>();

        Client cliente = clientRepository.findById(invoice.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Registro no econtrado"));

        double total = invoice.getItems().stream().mapToDouble(item -> {
            Product producto = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Registro no econtrado"));

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

    @Override
    public Invoice update(Invoice invoice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}