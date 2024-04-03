package com.example.clientes.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.clientes.domain.Client;
import com.example.clientes.domain.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InvoiceDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
	private String description;
    private Double total;
	private Date createAt;

	@JsonIgnoreProperties(value={"client", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	private Client client;

    @JsonIgnoreProperties(value={"items", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    private List<Item> items;
    
}
