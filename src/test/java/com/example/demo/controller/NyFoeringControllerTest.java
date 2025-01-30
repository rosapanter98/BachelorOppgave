package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Arrays;

import java.time.LocalDate;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
import com.example.demo.model.*;
import com.example.demo.service.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NyFoeringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerdiService verdiService;

    @MockBean
    private VerdiForingService verdiFøringService;

    @MockBean
    private StandardService standardService;

    @MockBean
    private SporsmolService sporsmolService;

    @MockBean
    private ForskriftService forskriftService;

    @MockBean
    private TilleggsinformasjonService tilleggsinformasjonService;

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testGetNyFoering() throws Exception {
        when(verdiService.hentAlleVerdier()).thenReturn(Arrays.asList(new Verdi("Title1")));
        when(forskriftService.hentAlleForskrift()).thenReturn(Arrays.asList(new Forskrift("Law1", "Description", LocalDate.now(), "URL")));

        mockMvc.perform(get("/nyfoering"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("verdier", "forskriftJson", "verdierJson", "parentSporsmolJson", "childSporsmolJson"))
               .andExpect(view().name("NyFoeringView"));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testPostNyFoering() throws Exception {
        MockMultipartFile forside_pdf = new MockMultipartFile("forside_pdf", "test.pdf", "application/pdf", "PDF content".getBytes());
        MockMultipartFile tilleggsinformasjon_pdf = new MockMultipartFile("tilleggsinformasjon_pdf", "info.pdf", "application/pdf", "PDF content".getBytes());

        mockMvc.perform(multipart("/nyfoering")
                        .file(forside_pdf)
                        .file(tilleggsinformasjon_pdf)
                        .param("verdi_tittel", "New Value")
                        .param("forskrift_tittel", "New Law")
                        .param("forskrift_beskrivelse", "Description")
                        .param("forskrift_publiseringsdato", "2021-01-01")
                        .param("forskrift_url", "http://example.com")
                        .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/nyfoering"));
        
        verify(verdiService).lagVerdi(any(Verdi.class));
        verify(forskriftService).lagForskrift(any(Forskrift.class));
    }
}
