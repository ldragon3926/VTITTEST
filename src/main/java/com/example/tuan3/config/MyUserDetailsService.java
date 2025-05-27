package com.example.tuan3.config;


import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return new User("admin", passwordEncoder().encode("123456"),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else if ("user".equals(username)) {
            return new User("user", passwordEncoder().encode("123456"),
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
        throw new UsernameNotFoundException("User not found");
    }
}
