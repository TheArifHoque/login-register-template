package com.arifhoque.main.service;

import com.arifhoque.main.model.Authority;
import com.arifhoque.main.model.User;
import com.arifhoque.main.repository.AuthorityRepo;
import com.arifhoque.main.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, AuthorityRepo authorityRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = authorityRepo.findByAuthority("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        userRepo.save(user);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = authorityRepo.findByAuthority("ROLE_ADMIN");
        user.setAuthorities(Set.of(authority));
        userRepo.save(user);
    }
}
