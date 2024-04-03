package com.example.clientes.dto;


import java.io.Serial;
import java.io.Serializable;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ClientCreateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String address;

}
