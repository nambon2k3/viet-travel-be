package com.fpt.capstone.tourism.model;

import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String full_name;
    private Gender gender;
    private String phone;
    private String address;
    private Role role;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

}

