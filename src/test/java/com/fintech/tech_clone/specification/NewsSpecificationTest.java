package com.fintech.tech_clone.specification;
import com.fintech.tech_clone.entity.News;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.AssertionErrors;
@SpringBootTest
@AutoConfigureMockMvc
class NewsSpecificationTest {
    @MockBean
    private Root<News> root;
    @MockBean
    private CriteriaQuery<?> query;
    @MockBean
    private CriteriaBuilder criteriaBuilder;
    @Test
    void hasCategory_WithValidCategory_ShouldReturnPredicate() {
        // Given
        String category = "Tech";
        Predicate mockPredicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.equal(root.get("news_category"), category)).thenReturn(mockPredicate);
        // When
        Specification<News> specification = NewsSpecification.hasCategory(category);
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockPredicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).equal(root.get("news_category"), category);
    }
    @Test
    void hasCategory_WithNullCategory_ShouldReturnNull() {
        // Given
        String category = null;
        // When
        Specification<News> specification = NewsSpecification.hasCategory(category);
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);
        // Then
        Assertions.assertNull(result);
    }
    @Test
    void hasContent_WithValidContent_ShouldReturnPredicate() {
        // Given
        String content = "Breaking News";
        Predicate mockPredicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.like(root.get("news_content"), "%Breaking News%")).thenReturn(mockPredicate);
        // When
        Specification<News> specification = NewsSpecification.hasContent(content);
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockPredicate, result);
    }
    @Test
    void hasContent_WithNullContent_ShouldReturnNull() {
        // Given
        String content = null;
        // When
        Specification<News> specification = NewsSpecification.hasContent(content);
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);
        // Then
        Assertions.assertNull(result);
    }
}