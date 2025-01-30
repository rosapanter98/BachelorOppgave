package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tilleggsinformasjon;
import com.example.demo.repository.TilleggsinformasjonRepo;

import jakarta.transaction.Transactional;

/**
 * Service class for managing additional information (Tilleggsinformasjon) in the application.
 * This service provides methods to create, retrieve, and delete additional information entries, particularly focusing on entries that contain PDF data.
 */
@Service
public class TilleggsinformasjonService {

    @Autowired
    private TilleggsinformasjonRepo tilleggsinformasjonRepo;

    /**
     * Retrieves additional information by its ID.
     * 
     * @param id the ID of the additional information to retrieve
     * @return an Optional containing the found additional information or an empty Optional if not found
     */
    @Transactional
    public Optional<Tilleggsinformasjon> hentTilleggsinformasjon(Long id) {
        return tilleggsinformasjonRepo.findById(id);
    }

    /**
     * Deletes additional information by its ID, if it exists.
     * 
     * @param id the ID of the additional information to delete
     */
    @Transactional
    public void slettTilleggsinformasjon(Long id) {
        tilleggsinformasjonRepo.findById(id).ifPresent(tilleggsinformasjonRepo::delete);
    }

    /**
     * Creates and saves a new additional information entry with PDF data.
     * 
     * @param pdfData the PDF data to be stored
     * @return the newly created and saved additional information
     */
    @Transactional
    public Tilleggsinformasjon lagTilleggsinformasjon(byte[] pdfData) {
        Tilleggsinformasjon tilleggsinformasjon = new Tilleggsinformasjon();
        tilleggsinformasjon.setPdfData(pdfData);
        return tilleggsinformasjonRepo.save(tilleggsinformasjon);
    }

    /**
     * Updates the PDF data of an existing additional information entry.
     * 
     * @param pdfData the new PDF data to be updated
     * @return the updated additional information entry
     * @throws IllegalStateException if no additional information entry is found for the hardcoded ID
     */
    @Transactional
    public Tilleggsinformasjon lagForside(byte[] pdfData) {
        Long eksisterendeId = 10L;  // This method assumes there's a predefined ID for the front page information
        return tilleggsinformasjonRepo.findById(eksisterendeId)
            .map(tilleggsinformasjon -> {
                tilleggsinformasjon.setPdfData(pdfData);
                return tilleggsinformasjonRepo.save(tilleggsinformasjon);
            })
            .orElseThrow(() -> new IllegalStateException("Ingen tilleggsinformasjon funnet med ID " + eksisterendeId));
    }

    /**
     * Retrieves an existing additional information entry assumed to be used as the front page.
     * 
     * @return the additional information entry used as the front page
     * @throws IllegalStateException if no additional information entry is found for the hardcoded ID
     */
    @Transactional
    public Tilleggsinformasjon hentForside() {
        Long eksisterendeId = 10L;  // This method assumes there's a predefined ID for the front page information
        return tilleggsinformasjonRepo.findById(eksisterendeId)
            .orElseThrow(() -> new IllegalStateException("Ingen tilleggsinformasjon funnet med ID " + eksisterendeId));
    }
}
