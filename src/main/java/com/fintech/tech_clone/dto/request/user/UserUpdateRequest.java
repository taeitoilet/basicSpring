package com.fintech.tech_clone.dto.request.user;
import com.fintech.tech_clone.validGroup.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotNull(message = "ROLE_NULL",groups = {UpdateGroup.class})
    @Positive(message = "INVALID_ROLE",groups = {UpdateGroup.class})
    private int role_id;
    @NotBlank(message = "PASSWORD_NULL",groups = {UpdateGroup.class})
    @Size(min = 6, message = "INVALID_PASSWORD",groups = {UpdateGroup.class})
    private String user_password;
    private String user_fullname;
    @Email(message = "INVALID_EMAIL",groups = {UpdateGroup.class})
    private String user_email;
    @Pattern(regexp = "^(03|07|08|09|012|016|018|019)+([0-9]{8})$", message = "INVALID_PHONE",groups = {UpdateGroup.class})
    private String user_phone;
    private String user_address;
    @Pattern(regexp = "([0-9]{12})$", message = "INVALID_CITIZEN",groups = {UpdateGroup.class})
    private String user_citizen_identification;
    @Past(message = "INVALID_DOB",groups = {UpdateGroup.class})
    private Date user_dob;
    @PositiveOrZero(message = "INVALID_MONEY",groups = {UpdateGroup.class})
    private int user_money;
    private LocalDate user_modified_date;
    private byte user_is_deleted;
}
