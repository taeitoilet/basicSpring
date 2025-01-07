package com.fintech.tech_clone.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblnews")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int news_id;
    private String news_category;
    private String news_content;
    private String news_title;
    private String news_author;
    private String news_img;
    private LocalDate news_create_date;
    private LocalDate news_modified_date;
    private String news_creater;
}
