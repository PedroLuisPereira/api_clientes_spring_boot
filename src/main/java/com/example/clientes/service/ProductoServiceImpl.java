package com.example.clientes.service;

import com.example.clientes.domain.Producto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Primary
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private List<Producto> productos = new ArrayList<>(Arrays.asList(
            new Producto(1, "producto1", 5000.0, 10),
            new Producto(2, "producto2", 6000.0, 10),
            new Producto(3, "producto3", 8000.0, 10),
            new Producto(4, "producto4", 10000.0, 10)
    ));

    @Override
    public List<Producto> getProductos() {

        log.info("Informacion");

        return productos;
    }
}
