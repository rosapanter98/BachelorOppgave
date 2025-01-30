package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Forskrift;
import com.example.demo.repository.ForskriftRepo;

import jakarta.transaction.Transactional;

/**
 * This class represents a service for managing Forskrift objects.
 */
@Service
public class ForskriftService {
    
    @Autowired
    public ForskriftRepo forskriftRepo;

    /**
     * Saves a Forskrift object to the repository.
     * 
     * @param forskrift The Forskrift object to be saved.
     */
    @Transactional
    public void lagForskrift(Forskrift forskrift){
        forskriftRepo.save(forskrift);
    }

    /**
     * Retrieves a Forskrift object from the repository based on its ID.
     * 
     * @param id The ID of the Forskrift object to retrieve.
     * @return An Optional containing the retrieved Forskrift object, or an empty Optional if not found.
     */
    @Transactional
    public Optional<Forskrift> hentForskrift(Long id) {
        return forskriftRepo.findById(id);
    }

    /**
     * Deletes a Forskrift object from the repository based on its ID.
     * 
     * @param id The ID of the Forskrift object to delete.
     */
    @Transactional
    public void slettForskrift(Long id){
        Optional<Forskrift> forskriftOpt=forskriftRepo.findById(id);
        if(forskriftOpt.isPresent()){
          Forskrift forskrift=forskriftOpt.get();
          forskriftRepo.delete(forskrift);  
        }
    }

    /**
     * Retrieves all Forskrift objects from the repository.
     * 
     * @return A list of all Forskrift objects in the repository.
     */
    @Transactional
    public List<Forskrift> hentAlleForskrift(){
        return forskriftRepo.findAll();
    }
}
