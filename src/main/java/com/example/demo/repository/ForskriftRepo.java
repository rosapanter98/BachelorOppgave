package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Forskrift;

@Repository
public interface ForskriftRepo extends JpaRepository<Forskrift, Long>{
    
}
