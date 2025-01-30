package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class GeneralNavigationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService; // Mock this if your security configuration requires it

    @Test
    public void home_ShouldReturnHomeView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(""))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("home"))
               .andExpect(model().attribute("currentPage", "home"));
    }

    @Test
    public void pdfOptions_ShouldReturnPdfOptionsView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/veileder/pdf-options"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("pdfOptionsView"))
               .andExpect(model().attribute("currentPage", "pdf-options"));
    }
    @WithMockUser(username="admnin@gmail.com", roles={"ADMIN"})
    @Test
    public void admin_ShouldReturnAdminView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("AdminView"))
               .andExpect(model().attribute("currentPage", "admin"));
    }

    @Test
    public void error_ShouldReturnErrorView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("error"))
               .andExpect(model().attribute("currentPage", "error"));
    }

    @Test
    public void about_ShouldReturnAboutView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("about"))
               .andExpect(model().attribute("currentPage", "about"));
    }

    @Test
    public void contact_ShouldReturnContactView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("contact"))
               .andExpect(model().attribute("currentPage", "contact"));
    }

    @Test
    public void login_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attribute("currentPage", "login"));
    }
}
