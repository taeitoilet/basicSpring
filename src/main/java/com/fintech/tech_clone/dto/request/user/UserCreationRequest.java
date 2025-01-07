package com.fintech.tech_clone.dto.request.user;
import com.fintech.tech_clone.validGroup.CreateGroup;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    @NotNull(message = "ROLE_NULL",groups = {CreateGroup.class})
    @Positive(message = "INVALID_ROLE",groups = {CreateGroup.class})
    private int role_id;
    @NotBlank(message = "USERNAME_NULL",groups = {CreateGroup.class})
    @Size(min = 3, message = "USERNAME_INVALID",groups = {CreateGroup.class})
    private String user_name;
    @NotBlank(message = "PASSWORD_NULL",groups = {CreateGroup.class})
    @Size(min = 6, message = "INVALID_PASSWORD",groups = {CreateGroup.class})
    private String user_password;
    private String user_fullname;
    @Email(message = "INVALID_EMAIL",groups = {CreateGroup.class})
    private String user_email;
    @Pattern(regexp = "^(03|07|08|09|012|016|018|019)+([0-9]{8})$", message = "INVALID_PHONE",groups = {CreateGroup.class})
    private String user_phone;
    private String user_address;
    @Pattern(regexp = "([0-9]{12})$", message = "INVALID_CITIZEN",groups = {CreateGroup.class})
    private String user_citizen_identification;
    @Past(message = "INVALID_DOB",groups = {CreateGroup.class})
    private Date user_dob;
    @PositiveOrZero(message = "INVALID_MONEY",groups = {CreateGroup.class})
    private int user_money;
    private LocalDate user_created_date;
}