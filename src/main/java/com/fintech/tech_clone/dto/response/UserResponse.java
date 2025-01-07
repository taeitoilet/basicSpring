package com.fintech.tech_clone.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fintech.tech_clone.entity.Role;
import lombok.*;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int user_id;
    private Role role;
    private String user_name;
    private String user_password;
    private String user_fullname;
    private String user_email;
    private String user_phone;
    private String user_address;
    private String user_citizen_identification;
    private Date user_dob;
    private String user_status;
}
