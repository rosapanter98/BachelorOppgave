package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.Sporsmol;
import com.example.demo.repository.SporsmolRepo;
import com.example.demo.repository.VerdiRepo;
import com.example.demo.repository.ForskriftRepo;
import com.example.demo.repository.TilleggsinformasjonRepo;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@SpringBootTest
public class SporsmolServiceTest {

    @Autowired
    private SporsmolService sporsmolService;

    @MockBean
    private SporsmolRepo sporsmolRepo;

    @MockBean
    private VerdiRepo verdiRepo;

    @MockBean
    private ForskriftRepo forskriftRepo;

    @MockBean
    private TilleggsinformasjonRepo tilleggsinformasjonRepo;

    @Test
    public void lagSporsmol_ShouldSaveSporsmol() {
        Sporsmol sporsmol = new Sporsmol();
        when(sporsmolRepo.save(any(Sporsmol.class))).thenReturn(sporsmol);

        Sporsmol savedSporsmol = sporsmolService.lagSporsmol(sporsmol);

        assertThat(savedSporsmol).isNotNull();
        verify(sporsmolRepo).save(sporsmol);
    }

    @Test
    public void hentSporsmol_ShouldReturnSporsmol() {
        Long id = 1L;
        Sporsmol sporsmol = new Sporsmol();
        when(sporsmolRepo.findById(id)).thenReturn(Optional.of(sporsmol));

        Optional<Sporsmol> result = sporsmolService.hentSporsmol(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(sporsmol);
    }

    /* @Test
    public void slettSporsmol_ShouldDeleteSporsmol() {
        Long id = 1L;
        Sporsmol sporsmol = new Sporsmol();
        when(sporsmolRepo.findById(id)).thenReturn(Optional.of(sporsmol));

        sporsmolService.slettSporsmol(id);

        verify(sporsmolRepo).delete(sporsmol);
    } */

    @Test
    public void hentAlleSporsmol_ShouldReturnAllSporsmol() {
        List<Sporsmol> sporsmolList = Arrays.asList(new Sporsmol(), new Sporsmol());
        when(sporsmolRepo.findAll()).thenReturn(sporsmolList);

        List<Sporsmol> results = sporsmolService.hentAlleSporsmol();

        assertThat(results).hasSize(sporsmolList.size());
        assertThat(results).containsAll(sporsmolList);
    }

    @Test
    public void hentAlleStartSporsmol_ShouldReturnStartSporsmol() {
        List<Sporsmol> allSporsmol = Arrays.asList(
            new Sporsmol() {{ setParents(Arrays.asList(new Sporsmol())); }},
            new Sporsmol() {{ setParents(Arrays.asList()); }}
        );
        when(sporsmolRepo.findAll()).thenReturn(allSporsmol);

        List<Sporsmol> results = sporsmolService.hentAlleStartSporsmol();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getParents()).isEmpty();
    }
}