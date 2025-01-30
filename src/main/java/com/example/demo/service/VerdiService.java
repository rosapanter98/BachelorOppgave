package com.example.demo.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Verdi;
import com.example.demo.repository.VerdiForingRepo;
import com.example.demo.repository.VerdiRepo;

import jakarta.transaction.Transactional;

/**
 * Service class for managing value entities (Verdi) in the application.
 * Provides methods for creating, retrieving, updating, and deleting values,
 * as well as managing associated relationships with value provisions (VerdiForing).
 */
@Service
public class VerdiService {
    
    @Autowired
    private VerdiRepo verdiRepo;

    @Autowired
    private VerdiForingRepo verdiForingRepo;

    /**
     * Saves a value entity to the repository.
     * 
     * @param verdi the value entity to save
     * @return the saved value entity
     */
    @Transactional
    public Verdi lagVerdi(Verdi verdi) {
        return verdiRepo.save(verdi);
    }
    
    /**
     * Retrieves a value entity by its ID.
     * 
     * @param id the ID of the value to retrieve
     * @return an Optional containing the found value or an empty Optional if no value is found
     */
    @Transactional
    public Optional<Verdi> hentVerdi(Long id) {
        return verdiRepo.findById(id);
    }
    
    /**
     * Deletes a value entity by its ID, including all associated VerdiForing entities.
     * 
     * @param id the ID of the value to delete
     */
    @Transactional
    public void slettVerdi(Long id) {
       verdiRepo.findById(id).ifPresent(verdi -> {
           verdi.getVerdiFøringer().forEach(verdiForingRepo::delete);
           verdiRepo.delete(verdi);
       });
    }

    /**
     * Retrieves all value titles concatenated as a single string.
     * 
     * @return a string containing all value titles separated by commas
     */
    @Transactional
    public String getAllTitles(){
        return verdiRepo.findAll().stream()
                        .map(Verdi::getTittel)
                        .collect(Collectors.joining(","));
    }   

    /**
     * Retrieves all value entities from the repository.
     * 
     * @return a list of all value entities
     */
    @Transactional
    public List<Verdi> hentAlleVerdier() {
        return verdiRepo.findAll();
    }

    /**
     * Updates the title of a value entity.
     * 
     * @param id the ID of the value to update
     * @param newTitle the new title to set for the value
     * @return true if the update was successful, false otherwise
     * @throws RuntimeException if no value is found with the provided ID
     */
    @Transactional
    public boolean updateVerdi(Long id, String newTitle) {
        return verdiRepo.findById(id).map(verdi -> {
            verdi.setTittel(newTitle);
            verdiRepo.save(verdi);
            return true;
        }).orElseThrow(() -> new RuntimeException("Verdi not found with id: " + id));
    }
}
