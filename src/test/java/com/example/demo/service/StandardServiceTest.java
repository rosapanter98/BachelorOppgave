package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.exceptions.StandardIkkeFunnetException;
import com.example.demo.model.Standard;
import com.example.demo.model.VerdiForing;
import com.example.demo.repository.StandardRepo;
import com.example.demo.repository.VerdiForingRepo;

@SpringBootTest
public class StandardServiceTest {

    @Autowired
    private StandardService standardService;

    @MockBean
    private StandardRepo standardRepo;

    @MockBean
    private VerdiForingRepo verdiForingRepo;

    @Test
    public void lagStandard_ShouldSaveStandard() {
        Standard standard = new Standard();
        when(standardRepo.save(any(Standard.class))).thenReturn(standard);

        Standard savedStandard = standardService.lagStandard(standard);

        assertThat(savedStandard).isNotNull();
        verify(standardRepo).save(standard);
    }

    @Test
    public void hentStandard_ShouldReturnStandard() {
        Long id = 1L;
        Standard standard = new Standard();
        when(standardRepo.findById(id)).thenReturn(Optional.of(standard));

        Standard result = standardService.hentStandard(id);

        assertThat(result).isEqualTo(standard);
    }

    @Test
    public void hentStandard_WhenNotExists_ShouldThrowException() {
        Long id = 1L;
        when(standardRepo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> standardService.hentStandard(id))
            .isInstanceOf(StandardIkkeFunnetException.class)
            .hasMessageContaining("Standard ikke funnet");
    }


    @Test
    public void lagStandard_WithDetails_ShouldCreateAndSaveStandard() {
        String tittel = "Standards";
        String nummer = "123";
        int publiseringsAr = 2021;
        Standard standard = new Standard("Standards", nummer, publiseringsAr);
        standardService.lagStandard(standard);
        assertThat(standard.getTittel()).isEqualTo(tittel);
        assertThat(standard.getStandardNummer()).isEqualTo(nummer);
        assertThat(standard.getPubliseringsAr()).isEqualTo(publiseringsAr);
        verify(standardRepo).save(standard);
    }

    @Test
    public void hentAlleStandarder_ShouldReturnAllStandards() {
        List<Standard> expectedStandards = Arrays.asList(new Standard(), new Standard());
        when(standardRepo.findAll()).thenReturn(expectedStandards);

        List<Standard> result = standardService.hentAlleStandarder();

        assertThat(result).hasSize(2).containsAll(expectedStandards);
    }
}
