package com.example.clientes.service;

import com.example.clientes.domain.Invoice;
import com.example.clientes.dto.InvoiceCreateDto;
import com.example.clientes.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    List<Invoice> listAll();

    InvoiceDto listById(int id);

    Invoice create(InvoiceCreateDto invoice);


}