package com.fintech.tech_clone.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbluser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String user_name;
    private String user_password;
    private String user_fullname;
    private String user_email;
    private String user_phone;
    private String user_address;
    private String user_citizen_identification;
    private Date user_dob;
    private int user_money;
    private LocalDate user_created_date;
    private LocalDate user_modified_date;
    private byte user_is_deleted;
    private String user_status;
    private String user_creater;
    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false) // Foreign key
    private Role role;
}