package com.arifhoque.main.controller;

import com.arifhoque.main.model.User;
import com.arifhoque.main.service.UserService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage(@RequestParam @Nullable String error, Model model) {
        if (error != null && error.equals("true"))
            model.addAttribute("error","Invalid credentials!");

        return "login.html";
    }

    @GetMapping("/user/register")
    public String userRegistrationPage() {
        return "register-user.html";
    }

    @PostMapping("/user/register")
    public String registerUser(User user, RedirectAttributes ra) {
        if (userService.findUserByUsername(user.getUsername()) != null) {
            ra.addFlashAttribute("error","User already exist with username: " + user.getUsername());
            return "redirect:/user/register";
        }
        userService.saveUser(user);
        ra.addFlashAttribute("success","Registration successful");

        return "redirect:/user/register";
    }
}
