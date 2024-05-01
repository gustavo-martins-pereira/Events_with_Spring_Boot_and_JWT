package com.vitai.events.services.auth;

import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.domain.enums.UserRole;
import com.vitai.events.repositories.UserRepository;
import com.vitai.events.usecases.auth.RegisterUsecase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("test")
class RegisterUsecaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterUsecase registerUsecase;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new User with success returning 201 with No Body")
    void testRegisterNewUserSuccess() {
        RegisterDTO registerDTO = new RegisterDTO("John Doe", "john", "password", UserRole.USER);
        when(this.userRepository.findByLogin(registerDTO.getLogin())).thenReturn(null);

        ResponseEntity<Object> response = this.registerUsecase.execute(registerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(this.userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should not create a new User because there is a User with same login")
    void testRegisterNewUserWithExistingLogin() {
        RegisterDTO registerDTO = new RegisterDTO("John Doe", "john", "password", UserRole.USER);
        when(this.userRepository.findByLogin(registerDTO.getLogin())).thenReturn(new User());

        ResponseEntity<Object> response = this.registerUsecase.execute(registerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username already exists", response.getBody());
        verify(this.userRepository, never()).save(any(User.class));
    }

}
