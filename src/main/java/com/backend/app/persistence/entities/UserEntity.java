package com.backend.app.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="tecfood_admin_user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name ="first_Name", nullable = false)
    private String firstName;

    @Column(name ="last_name", nullable = false)
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="email", unique = true, nullable = false, updatable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="dni")
    private String dni;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="is_google_account")
    private boolean isGoogleAccount;

    @Column(name="is_verified_email")
    private boolean isVerifiedEmail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @CreatedDate
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    // Datos extra de Django

    @Column(name = "is_staff", nullable = false)
    private boolean isStaff;

    @Column(name = "is_superuser", nullable = false)
    private boolean isSuperuser;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "date_joined", nullable = false, updatable = false)
    private LocalDateTime dateJoined;

    @Column(name = "last_login", nullable = false)
    private LocalDateTime lastLogin;

    @Column(name = "username", unique = true, nullable = true, length = 150)
    private String username;  // Nombre de usuario único (opcional)


}