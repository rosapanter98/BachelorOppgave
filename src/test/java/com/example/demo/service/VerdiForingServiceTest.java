package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.Standard;
import com.example.demo.model.Verdi;
import com.example.demo.model.VerdiForing;
import com.example.demo.repository.VerdiForingRepo;
import com.example.demo.repository.VerdiRepo;

@SpringBootTest
public class VerdiForingServiceTest {

    @Autowired
    private VerdiForingService verdiForingService;

    @MockBean
    private VerdiForingRepo verdiForingRepo;

    @MockBean
    private VerdiRepo verdiRepo;

    @Test
    public void lagVerdiforing_ShouldSaveValidEntries() {
        // Given
        Standard standard = new Standard();
        Map<String, String> verdiVerdier = new HashMap<>();
        verdiVerdier.put("Verdi1", "100");
        verdiVerdier.put("Verdi2", "200");

        Verdi verdi1 = new Verdi();
        verdi1.setTittel("Verdi1");
        Verdi verdi2 = new Verdi();
        verdi2.setTittel("Verdi2");

        when(verdiRepo.findByTittel("Verdi1")).thenReturn(verdi1);
        when(verdiRepo.findByTittel("Verdi2")).thenReturn(verdi2);

        // When
        verdiForingService.lagVerdiforing(standard, verdiVerdier);

        // Then
        verify(verdiRepo, times(1)).findByTittel("Verdi1");
        verify(verdiRepo, times(1)).findByTittel("Verdi2");
        verify(verdiForingRepo, times(2)).save(any(VerdiForing.class));
    }
    
    @Test
    public void lagVerdiforing_WithNonExistentVerdi_ShouldNotSave() {
        // Given
        Standard standard = new Standard();
        Map<String, String> verdiVerdier = new HashMap<>();
        verdiVerdier.put("Verdi1", "100");

        when(verdiRepo.findByTittel("Verdi1")).thenReturn(null);

        // When
        verdiForingService.lagVerdiforing(standard, verdiVerdier);

        // Then
        verify(verdiRepo, times(1)).findByTittel("Verdi1");
        verify(verdiForingRepo, never()).save(any(VerdiForing.class));
    }
}
