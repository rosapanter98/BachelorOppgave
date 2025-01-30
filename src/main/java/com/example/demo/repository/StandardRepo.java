package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Standard;

@Repository
public interface StandardRepo extends JpaRepository<Standard, Long> {
    
    


}