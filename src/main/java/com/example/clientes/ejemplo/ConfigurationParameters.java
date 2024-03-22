package com.example.clientes.ejemplo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ConfigurationParameters {

    private String nombre;
    private String lenguaje;
    private String pais;
    private String author;

    public ConfigurationParameters() {
    }

    public ConfigurationParameters(String nombre, String lenguaje, String pais, String author) {
        this.nombre = nombre;
        this.lenguaje = lenguaje;
        this.pais = pais;
        this.author = author;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ConfigurationParameters{" +
                "nombre='" + nombre + '\'' +
                ", lenguaje='" + lenguaje + '\'' +
                ", pais='" + pais + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
