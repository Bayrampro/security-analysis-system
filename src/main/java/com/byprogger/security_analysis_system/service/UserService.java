/**
 * Сервис пользователей: регистрация, создание стартовых аккаунтов и выдача списка пользователей.
 */

package com.byprogger.security_analysis_system.service;

import com.byprogger.security_analysis_system.dto.UserRegistrationDto;
import com.byprogger.security_analysis_system.entity.Role;
import com.byprogger.security_analysis_system.entity.User;
import com.byprogger.security_analysis_system.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(UserRegistrationDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalStateException("Пользователь с таким логином уже существует");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        return userRepository.save(user);
    }

    @Transactional
    public User createIfMissing(String username, String rawPassword, Role role) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(passwordEncoder.encode(rawPassword));
                    user.setRole(role);
                    return userRepository.save(user);
                });
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }
}
