package com.example.clientes.dto;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.example.clientes.domain.Invoice;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;


@AllArgsConstructor
@Getter
@Builder
public class ClientDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String address;

    @JsonIgnoreProperties(value={"client", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	private List<Invoice> invoices;

}
