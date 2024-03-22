package com.example.clientes.controller;


import com.example.clientes.domain.Client;
import com.example.clientes.domain.Invoice;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;
import com.example.clientes.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/invoices")
@CrossOrigin(origins = "http://localhost:4200")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<Invoice> listAll() {
        return invoiceService.listAll();
    }

    @GetMapping("/{id}")
    public InvoiceDto listById(@PathVariable int id) {
        return invoiceService.listById(id) ;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice create(@RequestBody InvoiceCreateDto invoice) {
        return invoiceService.create(invoice);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable int id, @RequestBody Client client) {
        client.setId(id);
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        
    }
}
