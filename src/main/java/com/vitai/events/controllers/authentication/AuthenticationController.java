package com.vitai.events.controllers.authentication;

import com.vitai.events.controllers.authentication.dtos.LoginDTO;
import com.vitai.events.controllers.authentication.dtos.LoginResponseDTO;
import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.repositories.UserRepository;
import com.vitai.events.usecases.auth.LoginUsecase;
import com.vitai.events.usecases.auth.RegisterUsecase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private RegisterUsecase registerUsecase;

    @Autowired
    private LoginUsecase loginUsecase;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new User"
    )
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return registerUsecase.execute(registerDTO);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Get a token to access some endpoints according the user role"
    )
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginDTO loginDTO) {
        var response = loginUsecase.execute(loginDTO);

        return ResponseEntity.ok(response);
    }

}
