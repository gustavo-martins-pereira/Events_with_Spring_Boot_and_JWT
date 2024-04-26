package com.vitai.events.controllers.authentication;

import com.vitai.events.controllers.authentication.dtos.LoginDTO;
import com.vitai.events.controllers.authentication.dtos.LoginResponseDTO;
import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.repositories.UserRepository;
import com.vitai.events.utils.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO registerDTO) {
        if(this.userRepository.findByLogin(registerDTO.login()) != null) ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = User.builder()
                .login(registerDTO.login())
                .password(encryptedPassword)
                .role(registerDTO.role())
                .build();

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
