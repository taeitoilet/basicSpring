package com.fintech.tech_clone.specification;
import com.fintech.tech_clone.entity.News;
import org.springframework.data.jpa.domain.Specification;
public class NewsSpecification {
    public static Specification<News> hasCategory(String category) {
        return ((root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.equal(root.get("news_category"),category));
    }
    public static Specification<News> hasContent(String content) {
        return ((root, query, criteriaBuilder) ->
                content == null? null : criteriaBuilder.like(root.get("news_content"), "%" + content + "%"));
    }
}