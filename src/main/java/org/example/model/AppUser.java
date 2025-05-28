package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

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
}
