package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.VerdiForing;

@Repository
public interface VerdiForingRepo extends JpaRepository<VerdiForing, Long> {
    
}
