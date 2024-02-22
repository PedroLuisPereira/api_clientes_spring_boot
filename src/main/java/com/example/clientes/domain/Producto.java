package com.example.clientes.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {

    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;



}
