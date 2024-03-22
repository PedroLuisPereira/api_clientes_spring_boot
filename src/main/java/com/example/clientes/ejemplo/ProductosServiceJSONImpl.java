package com.example.clientes.ejemplo;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;

//import java.io.IOException;
import java.util.List;

@Service
public class ProductosServiceJSONImpl implements ProductoService{
    @Override
    public List<Producto> getProductos() {

//        List<Producto> productos;
//
//        try {
//            productos = new ObjectMapper()
//                    .readValue(this.getClass().getResourceAsStream("/productos.json")),
//                    new TypeReference<List<Producto>>(){});
//        }catch (IOException ioException){
//        throw new RuntimeException();
//
//        }


        return null;
    }
}
