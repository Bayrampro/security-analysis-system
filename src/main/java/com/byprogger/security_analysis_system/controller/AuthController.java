package com.byprogger.security_analysis_system.controller;

import com.byprogger.security_analysis_system.dto.UserRegistrationDto;
import com.byprogger.security_analysis_system.entity.Role;
import com.byprogger.security_analysis_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserRegistrationDto());
        }
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("userForm") UserRegistrationDto userForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "register";
        }

        try {
            userService.register(userForm);
        } catch (IllegalStateException ex) {
            model.addAttribute("registerError", ex.getMessage());
            model.addAttribute("roles", Role.values());
            return "register";
        }

        return "redirect:/login?registered";
    }
}
