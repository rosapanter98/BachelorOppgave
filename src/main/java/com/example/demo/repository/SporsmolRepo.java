package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Sporsmol;

@Repository
public interface SporsmolRepo extends JpaRepository<Sporsmol, Long> {


}
//, JpaSpecificationExecutor<Sporsmol>