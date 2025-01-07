package com.fintech.tech_clone.controller;
import com.fintech.tech_clone.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(roles = "Admin")
    void getAllRoles_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/role"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
}