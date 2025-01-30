package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;
 
    @Test
    public void testCssResourceHandler() throws Exception {
        mockMvc.perform(get("/css/styles.css"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("text/css"));
    }

    @Test
    public void testJsResourceHandler() throws Exception {
        mockMvc.perform(get("/js/layoutScript.js"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/javascript"));
    }

    @Test
    public void testImageResourceHandler() throws Exception {
        mockMvc.perform(get("/images/ce_bilde.png"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("image/png"));
    }
}
