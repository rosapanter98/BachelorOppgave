package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void showRegistrationForm_ShouldDisplayRegistrationPage() throws Exception {
        mockMvc.perform(get("/register"))
               .andExpect(status().isOk())
               .andExpect(view().name("register"))
               .andExpect(model().attributeExists("user"))
               .andExpect(model().attribute("currentPage", "register"));
    }

    @Test
    public void registerUserAccount_PasswordsNotMatching_ShouldRedirectWithErrorMessage() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setPassword("123");
        dto.setConfirmPassword("321"); // Different password for testing mismatch

        mockMvc.perform(post("/register")
                        .flashAttr("user", dto))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/register"))
               .andExpect(flash().attribute("errorMessage", "Passwords must match!"));
    }

    @Test
    @WithMockUser
    public void registerUserAccount_PasswordsMatching_ShouldRedirectToHomePage() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setPassword("123");
        dto.setConfirmPassword("123"); // Matching password

        mockMvc.perform(post("/register")
                        .flashAttr("user", dto))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/home"));

        verify(userService).save(any(UserRegistrationDTO.class));
    }

}