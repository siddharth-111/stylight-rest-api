package com.example.SpringBoot.dao;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class LoginUserDetailsDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "roles")
    private String roles;
}
