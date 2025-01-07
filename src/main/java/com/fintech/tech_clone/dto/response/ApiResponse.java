package com.fintech.tech_clone.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code = 1000;
    private String message;
    private T result;
}
