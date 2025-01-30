package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Standard;
import com.example.demo.model.Verdi;
import com.example.demo.model.VerdiForing;
import com.example.demo.repository.VerdiForingRepo;
import com.example.demo.repository.VerdiRepo;

import jakarta.transaction.Transactional;

/**
 * Service class for managing value provisions (VerdiForing) related to standards.
 * This service provides methods to create value provisions entries, linking values to standards with numerical representations.
 */
@Service
public class VerdiForingService {

    @Autowired
    private VerdiForingRepo verdiFøringRepo;

    @Autowired
    private VerdiRepo verdiRepo;
    
    /**
     * Creates and persists value provisions (VerdiForing entities) for a given standard.
     * Each entry in the provided map represents a title and its corresponding value, which is expected to be numeric.
     * This method processes each entry, retrieves the corresponding Verdi entity by title, and if found,
     * creates a new VerdiForing entity with the numerical value and associated standard and value information.
     * 
     * @param standard the standard to which the value provisions are associated
     * @param verdiVerdier a map where each key is a value title and each value is the string representation of a numeric value
     * @throws NumberFormatException if the string value cannot be converted to a long
     */
    @Transactional
    public void lagVerdiforing(Standard standard, Map<String, String> verdiVerdier) {
        for (Map.Entry<String, String> entry : verdiVerdier.entrySet()) {
            String tittel = entry.getKey();
            String verdiStr = entry.getValue();
    
            try {
                Long numeriskVerdi = Long.parseLong(verdiStr);  // Assume the value is a valid number.
    
                Verdi verdi = verdiRepo.findByTittel(tittel);
                if (verdi == null) {
                    System.out.println("Ingen verdi funnet for tittel: " + tittel);
                    continue;
                }
    
                VerdiForing verdiFøringEntity = new VerdiForing();
                verdiFøringEntity.setStandard(standard);
                verdiFøringEntity.setNumeriskVerdi(numeriskVerdi);
                verdiFøringEntity.setVerdi(verdi);
                verdiFøringRepo.save(verdiFøringEntity);
                System.out.println("VerdiFøring lagret: " + verdiFøringEntity);
            } catch (NumberFormatException e) {
                System.out.println("Feil format på verdi for tittel " + tittel + ": " + verdiStr);
            }
        }
    }
}
