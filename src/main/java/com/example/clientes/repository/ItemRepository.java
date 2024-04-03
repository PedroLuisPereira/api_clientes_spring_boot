package com.example.clientes.repository;


import com.example.clientes.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {
    
}
