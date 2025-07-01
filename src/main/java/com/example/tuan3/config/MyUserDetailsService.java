package com.example.tuan3.config;

import com.example.tuan3.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.tuan3.entity.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // In ra để debug
        System.out.println("Encoded password in DB: " + user.getPassword());
        System.out.println("Test matches: " + passwordEncoder().matches("pass123" , user.getPassword()));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}

//@Service
//public class MyUserDetailsService implements UserDetailsService {
//        @Autowired
//        private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//public PasswordEncoder passwordEncoder(){
//    return new BCryptPasswordEncoder();
//}
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if ("admin".equals(username)) {
//            return new User("admin", passwordEncoder().encode("123456"),
//                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
//        } else if ("user".equals(username)) {
//            return new User("user", passwordEncoder().encode("123456"),
//                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
//        }
//        throw new UsernameNotFoundException("User not found");
//    }
//

//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.example.tuan3.entity.User user = userRepository.findByUsername(username).get();
//        if (username.equals(user.getUsername())) {
//            List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//            return new User(user.getUsername(),user.getPassword(),authorities);
//        }
//        throw new UsernameNotFoundException("User not found");
//    }
//


    //TESTDBUSER
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            String password = passwordEncoder.encode(user.getPassword());
//            List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//            return new org.springframework.security.core.userdetails.User(
//            user.getUsername(),
//            password, authorities
//    );
//    }


