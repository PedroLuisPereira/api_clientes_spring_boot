package com.example.clientes.repository;


import com.example.clientes.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}
