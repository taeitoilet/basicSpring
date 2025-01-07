package com.fintech.tech_clone.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private UserCreationRequest request;
    private User user;
    @BeforeEach
    private void initData(){
        request = new UserCreationRequest();
        request.setRole_id(2);
        request.setUser_name("quy12345");
        request.setUser_password("123456");
        user = new User();
        user.setUser_id(1);
        user.setUser_name("quy12345");
        user.setUser_password("$2a$10$h8Ug0wVyrpA5kSOn0YtNwesGF7cRMGdMsres6XT5Sin26jxyCTQOe");
    }
    @Test
    @WithMockUser(authorities = "create_data")
    void createUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
                );
    }
    @Test
    @WithMockUser(authorities = "create_data")
    void createUser_invalidUsername_fail() throws Exception {
        request.setUser_name("qu");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3 characters")
                );
    }
    @Test
    @WithMockUser(authorities = "create_data")
    void createUser_invalidRole_fail() throws Exception {
        request.setRole_id(-1);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Invalid Role")
                );
    }
    @Test
    @WithMockUser(authorities = "create_data")
    void createUser_invalidPassword_fail() throws Exception {
        request.setUser_password("123");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Password must be at least 8 characters")
                );
    }
    @Test
    @WithMockUser(authorities = "create_data")
    void createUser_nullRequest() throws Exception {
        request.setUser_name("");
        request.setUser_password("");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()
                );
    }
    @Test
    @WithMockUser(authorities = "update_data")
    void updateUser_validRequest_success() throws Exception {
        request.setUser_password("1234567");
        request.setRole_id(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/update/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
                );
    }
    @Test
    @WithMockUser(authorities = "update_data")
    void updateUser_nullRequest() throws Exception {
        request.setUser_password("");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/update/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()
                );
    }
    @Test
    @WithMockUser(roles = "Admin")
    void getAllUser_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser(roles = "Admin")
    void getUserbyId_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser()
    void unActiveUser_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/unactive/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser()
    void activeUser_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/active/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser(authorities = "delete_data")
    void delete_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser(authorities = "get_data")
    void getUser_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/find?username=admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser(authorities = "get_data")
    void findUserbyUserName_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/find?username=quy12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
}
