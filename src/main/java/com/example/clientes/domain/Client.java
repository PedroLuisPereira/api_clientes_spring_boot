package com.example.clientes.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "El campo nombre es requerido")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "El campo email es requerido")
    @Email(message = "Debe ser un email valido")
    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    /**
     * Relacion con facturas
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private List<Invoice> invoices;

    public Client() {
        invoices = new ArrayList<>();
    }

    public Client(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
