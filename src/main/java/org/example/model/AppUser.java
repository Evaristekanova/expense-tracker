package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @UuidGenerator
    private String id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
}
