package com.fintech.tech_clone.dto.request.news;
import com.fintech.tech_clone.validGroup.CreateGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsCreateRequest {
    @NotBlank(message = "CONTENT_NULL",groups = {CreateGroup.class})
    private String news_content;
    @NotBlank(message = "TITLE_NULL",groups = {CreateGroup.class})
    private String news_title;
    @NotBlank(message = "AUTHOR_NULL",groups = {CreateGroup.class})
    private String news_author;
    private String news_img;
    private LocalDate news_create_date;
}
