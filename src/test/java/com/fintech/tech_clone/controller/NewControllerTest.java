package com.fintech.tech_clone.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.tech_clone.dto.request.news.NewsCreateRequest;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.dto.response.NewsFindReponse;
import com.fintech.tech_clone.entity.News;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Collections;
@SpringBootTest
@AutoConfigureMockMvc
class NewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsService newsService;
    private NewsCreateRequest request;
    private News news;
    @BeforeEach
    private void initData(){
        request = new NewsCreateRequest();
        request.setNews_title("3D Secure là gì? 5 thông tin cần biết về 3D Secure");
        request.setNews_content("Khám phá vai trò và cách 3D Seucre bảo vệ giao dịch trực tuyến qua thẻ thanh toán và thẻ tín dụng của bạn. Tìm hiểu thêm ứng dụng của 3D Secure tại Techcombank.");
        request.setNews_author("quy1234");
        news = new News();
        news.setNews_title("3D Secure là gì? 5 thông tin cần biết về 3D Secure");
        news.setNews_content("Khám phá vai trò và cách 3D Seucre bảo vệ giao dịch trực tuyến qua thẻ thanh toán và thẻ tín dụng của bạn. Tìm hiểu thêm ứng dụng của 3D Secure tại Techcombank.");
        news.setNews_author("quy1234");
    }
    @Test
    @WithMockUser(authorities = "post_data")
    void createNews_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        Mockito.when(newsService.createNews(ArgumentMatchers.any())).thenReturn(news);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
                );
    }
    @Test
    @WithMockUser(authorities = "post_data")
    void createUser_nullRequest() throws Exception {
        request.setNews_title("");
        request.setNews_content("");
        request.setNews_author("");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()
                );
    }
    @Test
    @WithMockUser(authorities = "delete_data")
    void deletNews_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/news/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    void findNews() throws Exception {
        String title = "ti";
        News new1 = new News();
        new1.setNews_title("tiền gửi");
        News new2 = new News();
        new2.setNews_title("ti");
        ArrayList<News> arr = new ArrayList<>();
        arr.add(new1);
        arr.add(new2);
        Mockito.when(newsService.findNewsByTitleV2(ArgumentMatchers.any())).thenReturn(arr);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/findnews?title=" + title))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    void findNews_notFound() throws Exception {
        String title = "adjhgasghd";
        ArrayList<News> arr = new ArrayList<>();
        Mockito.when(newsService.findNewsByTitleV2(ArgumentMatchers.any())).thenReturn(arr);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/findnews?title=" + title))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    void getAllNews_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/news"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    @WithMockUser(authorities = "update_data")
    void updateNews_Success() throws Exception {
        News n = new News();
        n.setNews_category("category");
        n.setNews_title("title");
        n.setNews_content("content");
        n.setNews_author("quy1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(n);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/news/update/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    void searchNews_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/search")
                .param("category", "category")
                .param("content", "content"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    void getAllCategory_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/category"))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }
}