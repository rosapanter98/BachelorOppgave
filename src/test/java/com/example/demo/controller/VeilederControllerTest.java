package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.example.demo.service.SporsmolService;
import com.example.demo.service.TilleggsinformasjonService;
import com.example.demo.model.Sporsmol;
import com.example.demo.util.VeilederUtil;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="user", roles={"USER"})
public class VeilederControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SporsmolService sporsmolService;

    @MockBean
    private TilleggsinformasjonService tilleggsinformasjonService;

    @MockBean
    private VeilederUtil veilederUtil;  // Assuming VeilederUtil can be mocked


    @Test
    public void getVeileder_WithNoSessionData_ShouldInitiateSporsmolList() throws Exception {
        MockHttpSession session = new MockHttpSession();
        List<Sporsmol> emptyList = new ArrayList<>();
        when(sporsmolService.hentAlleStartSporsmol()).thenReturn(emptyList);

        mockMvc.perform(get("/veileder").session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("VeilederView"))
               .andExpect(model().attributeExists("sporsmolList"));
    }

    @Test
    public void getVeileder_WithCompleteSession_ShouldRedirectToPreparePdf() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("ferdig", true);

        mockMvc.perform(get("/veileder").session(session))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/veileder/prepare-pdf"));
    }

    @Test
    public void preparePdf_WithNoPdfPrepared_ShouldRedirectToLogic() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/veileder/prepare-pdf").session(session))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/veileder/prepare-pdf-logic"));
    }

    @Test
    public void preparePdf_WithPreparedPdf_ShouldReturnToOptionsView() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("preparedPdf", new ByteArrayInputStream(new byte[0]));

        mockMvc.perform(get("/veileder/prepare-pdf").session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("pdfOptionsView"));
    }

    @Test
    public void downloadPdf_ShouldTriggerPdfDownload() throws Exception {
        MockHttpSession session = new MockHttpSession();
        byte[] pdfContent = new byte[]{1, 2, 3};
        session.setAttribute("preparedPdfBytes", pdfContent);

        mockMvc.perform(get("/veileder/download-pdf").session(session))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/pdf"))
               .andExpect(header().string("Content-Disposition", "attachment; filename=standarder_og_poeng.pdf"));
    }

    @Test
    public void viewPdf_ShouldDisplayPdfInline() throws Exception {
        MockHttpSession session = new MockHttpSession();
        byte[] pdfContent = new byte[]{1, 2, 3};
        session.setAttribute("preparedPdfBytes", pdfContent);

        mockMvc.perform(get("/veileder/view-pdf").session(session))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/pdf"))
               .andExpect(header().string("Content-Disposition", "inline; filename=standarder_og_poeng.pdf"));
    }
}
