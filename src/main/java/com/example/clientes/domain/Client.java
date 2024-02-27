package com.example.clientes.domain;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
@Getter
@Setter
@ToString
public class Client {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique=true)
    private String username;
    private String password;
    private String nombre;


}
