package com.byprogger.security_analysis_system.dto;

import com.byprogger.security_analysis_system.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "Логин обязателен")
    @Size(min = 3, max = 100, message = "Логин должен быть от 3 до 100 символов")
    private String username;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, max = 120, message = "Пароль должен быть от 6 до 120 символов")
    private String password;

    @NotNull(message = "Роль обязательна")
    private Role role = Role.ANALYST;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
