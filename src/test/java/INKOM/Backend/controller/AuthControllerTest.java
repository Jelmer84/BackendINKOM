package INKOM.Backend.controller;

import INKOM.Backend.payload.request.LoginRequest;
import INKOM.Backend.payload.response.JwtResponse;
import INKOM.Backend.service.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private AuthController authController;

    @Test
    void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername("test");
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("Password");

        ResponseEntity<JwtResponse> ok = ResponseEntity.ok(new JwtResponse("jwt",
                1l,
                "test",
                "test@test.com", Arrays.asList("ROLE_USER", "ROLE_MODERATOR")));

        when(authorizationService.authenticateUser(loginRequest)).thenReturn(ok);
        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void registerUser() {
    }
}