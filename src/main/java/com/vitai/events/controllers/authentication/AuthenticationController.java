package com.vitai.events.controllers.authentication;

import com.vitai.events.controllers.authentication.dtos.LoginDTO;
import com.vitai.events.controllers.authentication.dtos.LoginResponseDTO;
import com.vitai.events.controllers.authentication.dtos.RegisterDTO;
import com.vitai.events.usecases.auth.LoginUsecase;
import com.vitai.events.usecases.auth.RegisterUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoints for user authentication")
@SecurityRequirements()
public class AuthenticationController {

    @Autowired
    private RegisterUsecase registerUsecase;

    @Autowired
    private LoginUsecase loginUsecase;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new User",
            description = "Creates a new user account with the provided credentials.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User successfully registered"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body or Username already exists"
                    )
            }
    )
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return this.registerUsecase.execute(registerDTO);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Get a token to access some endpoints according to the user role",
            description = "Authenticates a user and returns a JWT token for accessing protected endpoints.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful",
                            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    )
            }
    )
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginDTO loginDTO) {
        return this.loginUsecase.execute(loginDTO);
    }

}
