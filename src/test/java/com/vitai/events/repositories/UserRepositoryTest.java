package com.vitai.events.repositories;

import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.domain.User;
import com.vitai.events.domain.enums.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByLoginSuccess() {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("John Sam")
                .login("john")
                .password("password123")
                .role(UserRole.USER)
                .build();

        this.createUser(registerDTO);

        UserDetails userDetails = this.userRepository.findByLogin(registerDTO.getLogin());

        assertThat(userDetails).isNotNull();
    }

    @Test
    @DisplayName("Should not get User successfully from DB when User not exists")
    void findByLoginFail() {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("John Sam")
                .login("john")
                .password("password123")
                .role(UserRole.USER)
                .build();

        UserDetails userDetails = this.userRepository.findByLogin(registerDTO.getLogin());

        assertThat(userDetails).isNull();
    }

    private User createUser(RegisterDTO registerDTO) {
        User newUser = RegisterDTO.RegisterDTOToUser(registerDTO);

        entityManager.persist(newUser);

        return newUser;
    }
}