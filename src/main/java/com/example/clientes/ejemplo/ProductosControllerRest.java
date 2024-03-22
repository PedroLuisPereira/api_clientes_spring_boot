package com.example.clientes.ejemplo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos-pruebas")
public class ProductosControllerRest {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ConfigurationParameters configurationParameters;

    @GetMapping
    public ResponseEntity<?> getProductos(){

        System.out.println("Parámetros " + configurationParameters.toString());

        List<Producto> productos = productoService.getProductos();

        return ResponseEntity.ok(productos);
    }
}
