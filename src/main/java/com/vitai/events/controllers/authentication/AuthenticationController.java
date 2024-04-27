package com.vitai.events.controllers.authentication;

import com.vitai.events.controllers.authentication.dtos.LoginDTO;
import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.repositories.UserRepository;
import com.vitai.events.usecases.auth.LoginUsecase;
import com.vitai.events.usecases.auth.RegisterUsecase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterUsecase registerUsecase;

    @Autowired
    private LoginUsecase loginUsecase;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        registerUsecase.execute(registerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDTO loginDTO) {
        var response = loginUsecase.execute(loginDTO);

        return ResponseEntity.ok(response);
    }

}
