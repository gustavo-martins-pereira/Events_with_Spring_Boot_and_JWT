package com.vitai.events.services.auth;

import com.vitai.events.controllers.authentication.dtos.LoginDTO;
import com.vitai.events.controllers.authentication.dtos.LoginResponseDTO;
import com.vitai.events.domain.User;
import com.vitai.events.usecases.auth.LoginUsecase;
import com.vitai.events.utils.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("test")
class LoginUsecaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginUsecase loginUsecase;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        String username = "testUser";
        String password = "testPassword";

        LoginDTO loginDTO = new LoginDTO(username, password);

        User user = User.builder()
                .login(username)
                .password(password)
                .build();

        String token = "generated_token";
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        when(tokenService.generateToken(user)).thenReturn(token);

        ResponseEntity<LoginResponseDTO> responseEntity = loginUsecase.execute(loginDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(token, Objects.requireNonNull(responseEntity.getBody()).token());
    }

    @Test
    @DisplayName("Should return UNAUTHORIZED when login fails")
    void testLoginFailure() {
        LoginDTO loginDTO = new LoginDTO("invalid_username", "invalid_password");

        when(this.authenticationManager.authenticate(any())).thenThrow(new RuntimeException());

        ResponseEntity<LoginResponseDTO> response = this.loginUsecase.execute(loginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
