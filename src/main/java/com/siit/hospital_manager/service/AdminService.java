package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.repository.UserRepository;
import com.siit.hospital_manager.model.Admin;
import com.siit.hospital_manager.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Integer createUser(Admin user) {

        userRepository.findByUserName(user.getUserName()).ifPresent(u -> {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "User already exists!");
                }
        );

        User u = Admin.builder()
                .userName(user.getUserName())
                .password(passwordEncoder.encode(user.getPassword()))
                .isActive(true)
                .roles("ROLE_ADMIN")
                .build();
        return userRepository.save(u).getId();
    }

}