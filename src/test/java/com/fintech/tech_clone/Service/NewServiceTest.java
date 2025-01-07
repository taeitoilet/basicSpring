package com.fintech.tech_clone.Service;
import com.fintech.tech_clone.dto.request.news.NewsCreateRequest;
import com.fintech.tech_clone.dto.request.news.NewsUpdateRequest;
import com.fintech.tech_clone.entity.News;
import com.fintech.tech_clone.exception.ErrorCode;
import com.fintech.tech_clone.repository.NewsRepository;
import com.fintech.tech_clone.service.NewsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;
@SpringBootTest
class NewServiceTest {
    @Autowired
    private NewsService newsService;
    @MockBean
    private NewsRepository newsRepository;
    private NewsCreateRequest createRequest;
    private NewsUpdateRequest updateRequest;
    private News news;
    @BeforeEach
    private void initData(){
        createRequest = new NewsCreateRequest();
        updateRequest = new NewsUpdateRequest();
        news = new News();
    }
    @Test
    void createNews_onSuccess(){
        createRequest = new NewsCreateRequest();
        news = new News();
        createRequest.setNews_author("admin");
        createRequest.setNews_title("no title");
        createRequest.setNews_content("fake content");
        news.setNews_author(createRequest.getNews_author());
        news.setNews_title(createRequest.getNews_title());
        news.setNews_content(createRequest.getNews_content());
        Mockito.when(newsRepository.save(ArgumentMatchers.any(News.class))).thenReturn(news);
        News result = newsService.createNews(createRequest);
        Assertions.assertEquals("admin",result.getNews_author());
        Assertions.assertEquals("no title",result.getNews_title());
        Assertions.assertEquals("fake content",result.getNews_content());
    }
    @Test
    void getNews_onSuccess(){
        news = new News();
        news.setNews_author("admin");
        news.setNews_title("no title");
        news.setNews_content("fake content");
        List<News> list = Arrays.asList(news);
        Pageable pageable = PageRequest.of(0,10);
        Page<News> newPage = new PageImpl<>(list,pageable,list.size());
        Mockito.when(newsRepository.findAll(pageable)).thenReturn(newPage);
        Page<News> result = newsService.getNews(pageable);
        Assertions.assertNotNull(result);
    }
    @Test
    void getNewsById_onSuccess(){
        Mockito.when(newsRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(news));
        News result = newsService.getNewsById(1);
        Assertions.assertNotNull(result);
    }
    @Test
    void getNewsById_onFail(){
        Mockito.when(newsRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> newsService.getNewsById(1));
        Assertions.assertEquals("New not found",exception.getMessage() );
    }
    @Test
    void updateNews_onSuccess(){
        int newsId = 1;
        News existedNews = new News();
        existedNews.setNews_id(1);
        existedNews.setNews_author("admin");
        existedNews.setNews_title("no title");
        existedNews.setNews_content("fake content");
        updateRequest.setNews_author("admin");
        updateRequest.setNews_title("no title1");
        updateRequest.setNews_content("fake content");
        news = new News();
        news.setNews_author(updateRequest.getNews_author());
        news.setNews_title(updateRequest.getNews_title());
        news.setNews_content(updateRequest.getNews_content());
        Mockito.when(newsRepository.findById(newsId)).thenReturn(Optional.of(existedNews));
        Mockito.when(newsRepository.save(ArgumentMatchers.any(News.class))).thenReturn(news);
        News result = newsService.updateNews(newsId,updateRequest);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("no title1",result.getNews_title());
        Assertions.assertEquals("fake content",result.getNews_content());
    }
    @Test
    void updateNews_onFail(){
        Mockito.when(newsRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> newsService.updateNews(1,updateRequest));
        Assertions.assertEquals("New not found",exception.getMessage());
    }
    @Test
    void deleteNews_onSuccess() {
        int newsId = 1;
        Mockito.doNothing().when(newsRepository).deleteById(newsId);
        Assertions.assertDoesNotThrow(() -> newsService.deleteNews(newsId));
        Mockito.verify(newsRepository, Mockito.times(1)).deleteById(newsId);
    }
    @Test
    void deleteNews_whenNotFound_thenThrowException() {
        int newsId = 1;
        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(newsRepository).deleteById(newsId);
        EmptyResultDataAccessException exception = Assertions.assertThrows(
                EmptyResultDataAccessException.class,
                () -> newsService.deleteNews(newsId)
        );
        Mockito.verify(newsRepository, Mockito.times(1)).deleteById(newsId);
    }
    @Test
    void finByTitle_onSuccess(){
        news.setNews_author("admin");
        news.setNews_title("no title");
        news.setNews_content("fake content");
        ArrayList<News> list = new ArrayList<>();
        list.add(news);
        Mockito.when(newsRepository.findByTitle(ArgumentMatchers.anyString())).thenReturn(list);
        ArrayList<News> result = newsService.findNewsByTitleV2("no");
        Assertions.assertEquals(1,result.size());
    }
    @Test
    void searchNews_onSuccess(){
        news = new News();
        news.setNews_author("admin");
        news.setNews_title("title");
        news.setNews_content("content");
        news.setNews_category("category");
        List<News> list = Arrays.asList(news);
        String category = "cate";
        String content = "con";
        Pageable pageable = PageRequest.of(0, 10);
        Page<News> newsPage = new PageImpl<>(list, pageable,1);
        Mockito.when(newsRepository.findAll(ArgumentMatchers.any(Specification.class), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(newsPage);
        Page<News> result = newsService.searchNews(category, content, pageable);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals("title", result.getContent().get(0).getNews_title());
    }
    @Test
    void getAllCategory_Success(){
        ArrayList<String> categories = new ArrayList<>();
        categories.add("cate1");
        categories.add("cate2");
        Mockito.when(newsRepository.findAllCategory()).thenReturn(categories);
        ArrayList<String> result = newsService.getAllCategory();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("cate1", result.get(0));
        Assertions.assertEquals("cate2", result.get(1));
    }
}