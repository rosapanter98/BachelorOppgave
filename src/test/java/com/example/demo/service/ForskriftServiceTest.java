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

import com.example.demo.model.Forskrift;
import com.example.demo.repository.ForskriftRepo;

import jakarta.transaction.Transactional;
@Transactional
@SpringBootTest
public class ForskriftServiceTest {

    @Autowired
    private ForskriftService forskriftService;

    @MockBean
    private ForskriftRepo forskriftRepo;

    @Test
    public void lagForskrift_ShouldSaveForskrift() {
        Forskrift forskrift = new Forskrift();
        forskrift.setId(1L);
        forskrift.setTittel("Ny Forskrift");

        forskriftService.lagForskrift(forskrift);

        verify(forskriftRepo).save(forskrift);
    }

    @Test
    public void hentForskrift_ShouldReturnForskrift() {
        Forskrift forskrift = new Forskrift();
        forskrift.setId(1L);
        forskrift.setTittel("Eksisterende Forskrift");

        when(forskriftRepo.findById(1L)).thenReturn(Optional.of(forskrift));

        Optional<Forskrift> result = forskriftService.hentForskrift(1L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(forskrift);
    }

/*     @Test
    public void slettForskrift_ShouldDeleteForskrift() {
        Forskrift forskrift = new Forskrift();
        forskrift.setId(1L);

        when(forskriftRepo.findById(1L)).thenReturn(Optional.of(forskrift));

        forskriftService.slettForskrift(1L);

        verify(forskriftRepo).delete(forskrift);
    } */

    @Test
    public void hentAlleForskrift_ShouldReturnAllForskrift() {
        Forskrift forskrift1 = new Forskrift();
        forskrift1.setId(1L);
        Forskrift forskrift2 = new Forskrift();
        forskrift2.setId(2L);
        List<Forskrift> allForskrift = Arrays.asList(forskrift1, forskrift2);

        when(forskriftRepo.findAll()).thenReturn(allForskrift);

        List<Forskrift> result = forskriftService.hentAlleForskrift();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(forskrift1, forskrift2);
    }
}