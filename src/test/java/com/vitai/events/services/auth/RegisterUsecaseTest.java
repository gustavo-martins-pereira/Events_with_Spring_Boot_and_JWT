package com.vitai.events.services.auth;

import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.domain.enums.UserRole;
import com.vitai.events.repositories.UserRepository;
import com.vitai.events.usecases.auth.RegisterUsecase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class RegisterUsecaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterUsecase registerUsecase;

    @Test
    void testRegisterNewUserSuccess() {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("John Doe")
                .login("john")
                .password(new BCryptPasswordEncoder().encode("password123"))
                .role(UserRole.USER)
                .build();

        var user = registerUsecase.execute(registerDTO);


    }

}
