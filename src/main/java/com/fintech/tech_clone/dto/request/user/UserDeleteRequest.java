package com.fintech.tech_clone.dto.request.user;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDeleteRequest {
    private byte user_is_deleted;
}