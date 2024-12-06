package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.RoleRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.RoleService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "varchar(60)")
    private String fullName;

    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Column(columnDefinition = "varchar(400)")
    private String passwordHash;

    @Column(columnDefinition = "boolean default true")
    private boolean status;

    @Column
    private int role_id;

    @ManyToOne
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //new SimpleGrantedAuthority(role.getName())
        return List.of();
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return getEmail();
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
    }
}
