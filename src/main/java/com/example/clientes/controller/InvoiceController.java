package com.example.clientes.controller;


import com.example.clientes.domain.Client;
import com.example.clientes.domain.Invoice;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;
import com.example.clientes.service.InvoiceService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/invoices")
@CrossOrigin(origins = "http://localhost:4200")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> listAll() {
        return invoiceService.listAll();
    }

    @GetMapping("/{id}")
    public InvoiceDto listById(@PathVariable int id) {
        return invoiceService.listById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice create(@Valid @RequestBody InvoiceCreateDto invoice) {
        return invoiceService.create(invoice);
    }

}
