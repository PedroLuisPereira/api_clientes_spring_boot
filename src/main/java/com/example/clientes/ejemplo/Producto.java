package com.example.clientes.ejemplo;

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
