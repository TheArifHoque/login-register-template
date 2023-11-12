package com.arifhoque.main.service;

import com.arifhoque.main.model.Authority;
import com.arifhoque.main.model.User;
import com.arifhoque.main.repository.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetails(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with this username: " + username);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Authority authority : user.getAuthorities())
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
