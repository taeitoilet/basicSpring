package com.fintech.tech_clone.dto.response;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    String token;
    private boolean validate;
}