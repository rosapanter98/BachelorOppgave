package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.model.Forskrift;
import com.example.demo.model.Sporsmol;
import com.example.demo.model.Standard;
import com.example.demo.model.Verdi;
import com.example.demo.service.ForskriftService;
import com.example.demo.service.SporsmolService;
import com.example.demo.service.StandardService;
import com.example.demo.service.VerdiService;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SporsmolService sporsmolService;

    @MockBean
    private StandardService standardService;

    @MockBean
    private VerdiService verdiService;

    @MockBean
    private ForskriftService forskriftService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        Sporsmol sporsmol = new Sporsmol(); // assuming a constructor or setters to set properties
        Standard standard = new Standard();
        Verdi verdi = new Verdi();
        Forskrift forskrift = new Forskrift();

        when(sporsmolService.hentAlleSporsmol()).thenReturn(Arrays.asList(sporsmol));
        when(standardService.hentAlleStandarder()).thenReturn(Arrays.asList(standard));
        when(verdiService.hentAlleVerdier()).thenReturn(Arrays.asList(verdi));
        when(forskriftService.hentAlleForskrift()).thenReturn(Arrays.asList(forskrift));
    }

    @Test
    @WithMockUser(username="User@gmail.com", roles={"ADMIN"})
    public void testGetQuestions() throws Exception {
        mockMvc.perform(get("/slettfoering").with(SecurityMockMvcRequestPostProcessors
                .user("user").roles("USER")))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("sporsmolList", "standardsList", "verdiList", "forskriftList"));
    }

    @Test
    @WithMockUser(username="User@gmail.com", roles={"ADMIN"})
    public void testDeleteForskrifts() throws Exception {
        mockMvc.perform(post("/slettforskrift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("ids", List.of(1L))))
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))

               .andExpect(status().isOk())
               .andExpect(content().string("Deletion successful."));

        verify(forskriftService).slettForskrift(1L);
    }

    @Test
    @WithMockUser(username="User@gmail.com", roles={"ADMIN"})
    public void testDeleteForskriftsWithNoIdsProvided() throws Exception {
        mockMvc.perform(post("/slettforskrift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of()))
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))

               .andExpect(status().isBadRequest())
               .andExpect(content().string("No IDs provided."));
    }

    // Similar tests can be written for deleteQuestions, deleteStandard, and deleteVerdier
}