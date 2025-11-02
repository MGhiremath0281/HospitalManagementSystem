package com.springboot.hospitalmgmt.HospitalManagement.security;

import com.springboot.hospitalmgmt.HospitalManagement.models.User;
import com.springboot.hospitalmgmt.HospitalManagement.repository.UserRepositry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositry userRepositry;

    public CustomUserDetailsService(UserRepositry userRepositry) {
        this.userRepositry = userRepositry;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositry.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }
}
