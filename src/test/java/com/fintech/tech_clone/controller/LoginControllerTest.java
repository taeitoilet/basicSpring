package com.fintech.tech_clone.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.tech_clone.dto.request.login.LoginRequest;
import com.fintech.tech_clone.dto.response.LoginResponse;
import com.fintech.tech_clone.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Test
    void login_Success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUser_name("username");
        request.setUser_password("password");
        log.info(request.getUser_name() + " " + request.getUser_password());
        LoginResponse response = new LoginResponse();
        response.setValidate(true);
        response.setToken("token");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        Mockito.when(userService.authenticate(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .contentType("application/json")
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.token").value("token")
                );
    }
}
