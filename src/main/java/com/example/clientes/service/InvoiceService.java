package com.example.clientes.service;

import com.example.clientes.domain.Invoice;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    List<Invoice> listAll();

    InvoiceDto listById(int id);

    Invoice listByEmail(String email);

    Invoice create(InvoiceCreateDto invoice);

    Invoice update(Invoice invoice);

    void delete(int id);


}