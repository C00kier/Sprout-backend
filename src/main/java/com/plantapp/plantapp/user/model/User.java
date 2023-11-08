package com.plantapp.plantapp.user.model;

import com.plantapp.plantapp.user_type.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = true)
    private String photoUrl;


    public User(String email, String password, String userName){}

    private boolean isActive;

    public User(String email, String password, String userName){
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userType = UserType.USER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(UserType.USER.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;

    public User(int userId){
        this.userId = userId;

    }
}
