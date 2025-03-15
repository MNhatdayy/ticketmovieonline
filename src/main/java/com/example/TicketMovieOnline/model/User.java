package com.example.TicketMovieOnline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;
    @Column(name = "username", length = 50, unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    private String username;

    @Column(name = "password", length = 250)
    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "email", length = 50, unique = true)
    @NotBlank(message = "Email is required")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    @Email
    private String email;

    @Column(name = "phone", length = 50, unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Phone must be between 1 and 50 characters")
    private String phone;
    @Enumerated(value = EnumType.STRING)
    Role role;
    private String avatar;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public @NotBlank(message = "Username is required") @Size(min = 1, max = 50, message = "Phone must be between 1 and 50 characters") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Username is required") @Size(min = 1, max = 50, message = "Phone must be between 1 and 50 characters") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Email is required") @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters") @Email String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Username is required") @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters") String username) {
        this.username = username;
    }
}
