package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.StandardIkkeFunnetException;
import com.example.demo.model.Standard;
import com.example.demo.repository.StandardRepo;
import com.example.demo.repository.VerdiForingRepo;

import jakarta.transaction.Transactional;


/**
 * Service class for managing standards within the application.
 * Provides transactional methods to create, retrieve, update, and delete standards,
 * ensuring data consistency and handling relationships with associated entities like VerdiForing.
 */
@Service
public class StandardService {

    @Autowired
    private StandardRepo standardRepo;
    
    @Autowired
    private VerdiForingRepo verdiForingRepo;

    /**
     * Saves a standard entity to the repository.
     *
     * @param standard the standard to save
     * @return the saved standard
     */
    @Transactional
    public Standard lagStandard(Standard standard) {
        return standardRepo.save(standard);
    }

    /**
     * Creates and saves a new standard with the provided details.
     *
     * @param tittel the title of the standard
     * @param nummer the standard number
     * @param publiseringsAr the publication year of the standard
     * @return the newly created and saved standard
     */
    @Transactional
    public Standard lagStandard(String tittel, String nummer, int publiseringsAr) {
        return standardRepo.save(new Standard(tittel, nummer, publiseringsAr));
    }

    /**
     * Retrieves a standard by its ID or throws an exception if not found.
     *
     * @param id the ID of the standard to retrieve
     * @return the retrieved standard
     * @throws StandardIkkeFunnetException if the standard is not found
     */
    @Transactional
    public Standard hentStandard(Long id) {
        return standardRepo.findById(id).orElseThrow(() -> new StandardIkkeFunnetException("Standard ikke funnet"));
    }

    /**
     * Deletes a standard by its ID, including all associated VerdiForing entities.
     *
     * @param id the ID of the standard to delete
     */
    @Transactional
    public void slettStandard(Long id) {
        standardRepo.findById(id).ifPresent(standard -> {
            standard.getVerdiFøringer().forEach(verdiForingRepo::delete);
            standardRepo.delete(standard);
        });
    }

    /**
     * Updates the details of an existing standard.
     *
     * @param standard the standard to update with new data
     */
    @Transactional
    public void oppdaterStandard(Standard standard) {
        standardRepo.save(standard);
    }

    /**
     * Creates and saves a new standard with amendments.
     *
     * @param tittel the title of the standard
     * @param nummer the standard number
     * @param publiseringsAr the publication year of the standard
     * @param amendments a list of amendments related to the standard
     * @return the newly created and saved standard
     */
    @Transactional
    public Standard lagStandard(String tittel, String nummer, int publiseringsAr, List<String> amendments) {
        Standard standard = new Standard();
        standard.setTittel(tittel);
        standard.setStandardNummer(nummer);
        standard.setPubliseringsAr(publiseringsAr);
        standard.setAmendments(amendments);
        return standardRepo.save(standard);
    }

    /**
     * Retrieves all standards from the repository.
     *
     * @return a list of all standards
     */
    public List<Standard> hentAlleStandarder() {
        return standardRepo.findAll();
    }

    /**
     * Updates the title of a standard.
     *
     * @param id the ID of the standard to update
     * @param newTitle the new title for the standard
     * @return true if the update was successful, false otherwise
     */
    @Transactional
    public boolean updateStandard(Long id, String newTitle) {
        return standardRepo.findById(id).map(standard -> {
            standard.setTittel(newTitle);
            standardRepo.save(standard);
            return true;
        }).orElse(false);
    }
}
