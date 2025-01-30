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

import com.example.demo.model.Verdi;
import com.example.demo.model.VerdiForing;
import com.example.demo.repository.VerdiRepo;
import com.example.demo.repository.VerdiForingRepo;

@SpringBootTest
public class VerdiServiceTest {

    @Autowired
    private VerdiService verdiService;

    @MockBean
    private VerdiRepo verdiRepo;

    @MockBean
    private VerdiForingRepo verdiForingRepo;

    @Test
    public void lagVerdi_ShouldSaveVerdi() {
        Verdi verdi = new Verdi();
        when(verdiRepo.save(any(Verdi.class))).thenReturn(verdi);

        Verdi savedVerdi = verdiService.lagVerdi(verdi);

        assertThat(savedVerdi).isNotNull();
        verify(verdiRepo).save(verdi);
    }

    @Test
    public void hentVerdi_ShouldReturnVerdi() {
        Long id = 1L;
        Verdi verdi = new Verdi();
        when(verdiRepo.findById(id)).thenReturn(Optional.of(verdi));

        Optional<Verdi> result = verdiService.hentVerdi(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(verdi);
    }

    @Test
    public void getAllTitles_ShouldReturnConcatenatedTitles() {
        List<Verdi> allVerdi = Arrays.asList(new Verdi("Title1"), new Verdi("Title2"));
        when(verdiRepo.findAll()).thenReturn(allVerdi);

        String titles = verdiService.getAllTitles();

        assertThat(titles).isEqualTo("Title1,Title2");
    }

    @Test
    public void hentAlleVerdier_ShouldReturnAllVerdi() {
        List<Verdi> expectedVerdier = Arrays.asList(new Verdi(), new Verdi());
        when(verdiRepo.findAll()).thenReturn(expectedVerdier);

        List<Verdi> result = verdiService.hentAlleVerdier();

        assertThat(result).hasSize(expectedVerdier.size()).containsAll(expectedVerdier);
    }

    @Test
    public void updateVerdi_ShouldUpdateTitle() {
        Long id = 1L;
        Verdi existingVerdi = new Verdi();
        existingVerdi.setTittel("Old Title");
        when(verdiRepo.findById(id)).thenReturn(Optional.of(existingVerdi));

        boolean updated = verdiService.updateVerdi(id, "New Title");

        assertThat(updated).isTrue();
        verify(verdiRepo).save(existingVerdi);
        assertThat(existingVerdi.getTittel()).isEqualTo("New Title");
    }

    @Test
    public void updateVerdi_WhenNotFound_ShouldThrowException() {
        Long id = 1L;
        when(verdiRepo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> verdiService.updateVerdi(id, "New Title"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Verdi not found with id: " + id);
    }
}
