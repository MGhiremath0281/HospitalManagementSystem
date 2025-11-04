package com.springboot.hospitalmgmt.HospitalManagement.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Import for clarity
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user in the system and implements Spring Security's UserDetails.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Ensure BaseEntity provides the primary key (@Id)
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    // Typically used as the login username in getUsername()
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled = true;
    private boolean accountLocked = false;

    // Standard practice for user roles. FetchType.EAGER is common for UserDetails.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // ---------------------- Spring Security Methods (UserDetails)
    // ----------------------

    /**
     * Maps the Set of Role entities to a Collection of GrantedAuthority objects.
     * Roles are typically prefixed with "ROLE_" (e.g., "ROLE_ADMIN").
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                // SimpleGrantedAuthority is the preferred implementation
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the identifier used for login/authentication.
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Simple implementation: accounts never expire
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Simple implementation: credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}