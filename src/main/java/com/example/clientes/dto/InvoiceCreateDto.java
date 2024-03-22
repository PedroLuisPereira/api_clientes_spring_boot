package com.example.clientes.dto;

import java.io.Serializable;
import java.util.List;
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
public class InvoiceCreateDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private String description;
    private int clientId;
    private List<ItemCreateDto> items;
    
}
