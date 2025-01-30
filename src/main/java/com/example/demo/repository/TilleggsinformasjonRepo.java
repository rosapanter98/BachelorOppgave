package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tilleggsinformasjon;

@Repository
public interface TilleggsinformasjonRepo extends JpaRepository<Tilleggsinformasjon, Long> {
    
}
