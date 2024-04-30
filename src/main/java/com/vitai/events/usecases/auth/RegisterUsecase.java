package com.vitai.events.usecases.auth;

import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUsecase {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> execute(RegisterDTO registerDTO) {
        if(this.userRepository.findByLogin(registerDTO.getLogin()) != null) return ResponseEntity.badRequest().body("Username already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
        User newUser = User.builder()
                .name(registerDTO.getName())
                .login(registerDTO.getLogin())
                .password(encryptedPassword)
                .role(registerDTO.getRole())
                .build();

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
