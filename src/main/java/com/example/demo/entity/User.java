package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name_user")
    String name;

    @Column(name = "email_user")
    String emailUser;

    @Column(name = "password_user")
    String password;

    @ManyToMany(mappedBy = "userSet", fetch = FetchType.EAGER)
    Set<Product> productSet;

    public User(String name, String emailUser, String password) {
        this.name = name;
        this.emailUser = emailUser;
        this.password = password;
    }

    @Override
    public String toString() {
        return "name: " + this.name + "; " + "email: " + this.emailUser;
    }
}
