package com.example.clientes.dto;


import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "El campo name es requerido")
    private String name;

    @NotEmpty(message = "El campo email es requerido")
    @Email(message = "Debe ser un email valido")
    private String email;

    private String address;

}
