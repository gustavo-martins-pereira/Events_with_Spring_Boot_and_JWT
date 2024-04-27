package com.vitai.events.usecases.auth;

import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUsecase {

    @Autowired
    private UserRepository userRepository;

    public void execute(RegisterDTO registerDTO) {
        if(this.userRepository.findByLogin(registerDTO.login()) != null) ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = User.builder()
                .name(registerDTO.name())
                .login(registerDTO.login())
                .password(encryptedPassword)
                .role(registerDTO.role())
                .build();

        this.userRepository.save(newUser);
    }

}
