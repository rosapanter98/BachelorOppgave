package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Forskrift;
import com.example.demo.model.Sporsmol;
import com.example.demo.model.Tilleggsinformasjon;
import com.example.demo.model.Verdi;
import com.example.demo.repository.ForskriftRepo;
import com.example.demo.repository.SporsmolRepo;
import com.example.demo.repository.TilleggsinformasjonRepo;
import com.example.demo.repository.VerdiRepo;

import jakarta.transaction.Transactional;

@Service
public class SporsmolService {

    @Autowired
    private SporsmolRepo sporsmolRepo;
    @Autowired
    private VerdiRepo verdiRepo;
    @Autowired
    private ForskriftRepo forskriftRepo;
    @Autowired
    private TilleggsinformasjonRepo tilleggsinformasjonRepo;

    /**
     * Saves a question entity to the repository.
     *
     * @param sporsmol the question to save
     * @return the saved question
     */
    @Transactional
    public Sporsmol lagSporsmol(Sporsmol sporsmol) {
        return sporsmolRepo.save(sporsmol);
    }

    /**
     * Retrieves a question by its ID.
     *
     * @param id the ID of the question to retrieve
     * @return an Optional containing the found question or an empty Optional if no question is found
     */
    @Transactional
    public Optional<Sporsmol> hentSporsmol(Long id) {
        return sporsmolRepo.findById(id);
    }

    /**
     * Deletes a question by its ID, if it exists, including cleaning up all related entities.
     *
     * @param id the ID of the question to delete
     */
    @Transactional
    public void slettSporsmol(Long id) {
        sporsmolRepo.findById(id).ifPresent(sporsmolRepo::delete);
    }

    /**
     * Retrieves all questions from the repository.
     *
     * @return a list of all questions
     */
    @Transactional
    public List<Sporsmol> hentAlleSporsmol() {
        return sporsmolRepo.findAll();
    }

    /**
     * Retrieves all starting questions which have no parents.
     *
     * @return a list of starting questions
     */
    @Transactional
    public List<Sporsmol> hentAlleStartSporsmol() {
        return sporsmolRepo.findAll().stream()
                .filter(sporsmol -> sporsmol.getParents().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Creates a new question with specified attributes, including relationships to parents, children, values, regulations,
     * and additional information.
     *
     * @param tittel the title of the question
     * @param beskrivelse the description of the question
     * @param parentSporsmolIds a list of IDs for parent questions
     * @param childSporsmolIds a list of IDs for child questions
     * @param verdiId a list of values associated with the question
     * @param forskrift_ids a list of IDs for associated regulations
     * @param tilleggsinformasjonId the ID of associated additional information
     * @return the newly created question or null if an error occurs during creation
     */
    @Transactional
    public Sporsmol lagSporsmol(String tittel, String beskrivelse, List<Long> parentSporsmolIds, List<Long> childSporsmolIds, List<Verdi> verdiId, List<Long> forskrift_ids, Long tilleggsinformasjonId) {
        Sporsmol sporsmol = new Sporsmol();
        sporsmol.setTittel(tittel);
        sporsmol.setBeskrivelse(beskrivelse);

        try {
            // Handle parent questions
            if (parentSporsmolIds != null && !parentSporsmolIds.isEmpty()) {
                for (Long parentId : parentSporsmolIds) {
                    Sporsmol parent = sporsmolRepo.findById(parentId).orElse(null);
                    if (parent != null) {
                        sporsmol.addParent(parent);
                    }
                }
            }
        
            // Handle child questions
            if (childSporsmolIds != null && !childSporsmolIds.isEmpty()) {
                for (Long childId : childSporsmolIds) {
                    Sporsmol child = sporsmolRepo.findById(childId).orElse(null);
                    if (child != null) {
                        sporsmol.addChild(child);
                    }
                }
            }
        
            // Handle associations with Verdi objects
            if (verdiId != null && !verdiId.isEmpty()) {
                sporsmol.setVerdier(verdiId);  // Assuming `Sporsmol` has a setVerdier() method.
            }
            if (forskrift_ids != null && !forskrift_ids.isEmpty()) {
                List<Forskrift> forskrifter = forskrift_ids.stream()
                    .map(forskriftRepo::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
                sporsmol.setForskrifter(forskrifter);
            }
        
        // Handle Tilleggsinformasjon if provided
        if (tilleggsinformasjonId != null) {
            Tilleggsinformasjon tilleggsinformasjon = tilleggsinformasjonRepo.findById(tilleggsinformasjonId).orElse(null);
            sporsmol.setTilleggsinformasjon(tilleggsinformasjon);
        }
            
        
            // Save the question in the database
            return sporsmolRepo.save(sporsmol);
        } catch (IllegalStateException e) {
            // Log the exception, handle it, or notify the user
            System.err.println("Error creating Sporsmol: " + e.getMessage());
            // Depending on your architecture, you might need to throw a custom exception or return null
            return null;
        }

        
    } 
     /**
    * Updates a question with new details including the title and relationships to parents, children, and associated values.
    * This method will also check and manage any potential cycles that could be created by updating parent or child references.
    *
    * @param id the ID of the question to update
    * @param newTitle the new title for the question
    * @param childIds a list of IDs for the new child questions to be associated with the question
    * @param parentIds a list of IDs for the new parent questions to be associated with the question
    * @param verdiIds a list of IDs for new values to be associated with the question
    * @return true if the update was successful, false if the question is not found or if a cycle or mismatch in values is detected
    */
    
    @Transactional
    public boolean updateSporsmol(Long id, String newTitle, List<Long> childIds, List<Long> parentIds, List<Long> verdiIds) {
        Optional<Sporsmol> sporsmolOpt = sporsmolRepo.findById(id);
        if (!sporsmolOpt.isPresent()) {
            return false; // Question not found
        }

        Sporsmol sporsmol = sporsmolOpt.get();
        sporsmol.setTittel(newTitle); // Update title

        // Update children
        List<Sporsmol> newChildren = sporsmolRepo.findAllById(childIds);
        for (Sporsmol child : newChildren) {
            try {
                sporsmol.addChild(child);
            } catch (IllegalStateException e) {
                // Handle the case where a cycle would be created
                return false;
            }
        }

        // Update parents
        List<Sporsmol> newParents = sporsmolRepo.findAllById(parentIds);
        for (Sporsmol parent : newParents) {
            try {
                sporsmol.addParent(parent);
            } catch (IllegalStateException e) {
                // Handle the case where a cycle would be created
                return false;
            }
        }

        // Update verdier
        List<Verdi> newVerdier = verdiRepo.findAllById(verdiIds);
        if (newVerdier.size() != verdiIds.size()) {
            return false; // Some verdi IDs were not found
        }
        sporsmol.setVerdier(newVerdier); // Set the new verdier

        // Persist changes
        sporsmolRepo.save(sporsmol);
        return true;
    }
}