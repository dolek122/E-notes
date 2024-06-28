package com.arekproject.studiaprojekt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserDtls{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "full_name")
    private String name;
    private String email;
    private String password;
    private String about;
    private String role;
}
