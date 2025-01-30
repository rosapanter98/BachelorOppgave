package com.example.demo.config;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest

@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAuthenticateUserWithValidCredentials() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "Saus@gmail.com")
                        .param("password", "123123")
                        ) // Including CSRF token is important if CSRF is enabled
               .andExpect(status().isFound())
               .andExpect(redirectedUrl("/home"))
               .andExpect(authenticated().withUsername("Saus@gmail.com"));
    }

    @Test
    public void shouldRedirectToLoginIfNotAuthenticated() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isFound()) // expect a redirection
               .andExpect(redirectedUrl("http://localhost/home")); // redirection URL depends on your config
    }

    @Test
    public void shouldAllowAccessToPublicUrls() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk());
    }
}
