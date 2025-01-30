package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.Tilleggsinformasjon;
import com.example.demo.repository.TilleggsinformasjonRepo;

@SpringBootTest
public class TilleggsinformasjonServiceTest {

    @Autowired
    private TilleggsinformasjonService tilleggsinformasjonService;

    @MockBean
    private TilleggsinformasjonRepo tilleggsinformasjonRepo;

    @Test
    public void lagTilleggsinformasjon_ShouldSaveData() {
        byte[] pdfData = new byte[]{1, 2, 3};
        Tilleggsinformasjon tilleggsinformasjon = new Tilleggsinformasjon();
        when(tilleggsinformasjonRepo.save(any(Tilleggsinformasjon.class))).thenReturn(tilleggsinformasjon);

        Tilleggsinformasjon saved = tilleggsinformasjonService.lagTilleggsinformasjon(pdfData);

        assertThat(saved).isNotNull();
        verify(tilleggsinformasjonRepo).save(any(Tilleggsinformasjon.class));
    }

    @Test
    public void hentTilleggsinformasjon_WhenExists_ShouldReturnData() {
        Long id = 1L;
        Tilleggsinformasjon tilleggsinformasjon = new Tilleggsinformasjon();
        when(tilleggsinformasjonRepo.findById(id)).thenReturn(Optional.of(tilleggsinformasjon));

        Optional<Tilleggsinformasjon> result = tilleggsinformasjonService.hentTilleggsinformasjon(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(tilleggsinformasjon);
    }

    @Test
    public void hentForside_WhenExists_ShouldReturnData() {
        Tilleggsinformasjon forside = new Tilleggsinformasjon();
        when(tilleggsinformasjonRepo.findById(10L)).thenReturn(Optional.of(forside));

        Tilleggsinformasjon result = tilleggsinformasjonService.hentForside();

        assertThat(result).isEqualTo(forside);
    }

}
