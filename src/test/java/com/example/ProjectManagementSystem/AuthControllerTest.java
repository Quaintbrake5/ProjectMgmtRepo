package com.example.ProjectManagementSystem;

        import com.example.ProjectManagementSystem.controllers.AuthController;
        import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
        import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
        import com.example.ProjectManagementSystem.dtos.Responses.LoginResponse;
        import com.example.ProjectManagementSystem.dtos.Responses.RegisterResponse;
        import com.example.ProjectManagementSystem.services.AuthService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.springframework.http.ResponseEntity;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.Mockito.*;
        import static org.mockito.MockitoAnnotations.openMocks;

class AuthControllerTest {

            @Mock
            private AuthService authService;

            @InjectMocks
            private AuthController authController;

            @BeforeEach
            void setUp() {
                openMocks(this);
            }

            @Test
            void testRegister() {
                RegisterRequest request = new RegisterRequest();
                RegisterResponse response = new RegisterResponse();
                when(authService.register(request)).thenReturn(response);

                ResponseEntity<RegisterResponse> result = authController.register(request);

                assertEquals(200, result.getStatusCodeValue());
                assertEquals(response, result.getBody());
                verify(authService, times(1)).register(request);
            }


        @Test
        void testLogin() {
            LoginRequest request = new LoginRequest();
            // Fill with test data as needed
            LoginResponse response = new LoginResponse(
                1L, // id
                "testuser", // username
                "test@email.com", // email
                "testToken", // token
                "ROLE_USER", // role
                123L, // projectId or similar
                java.time.Instant.now() // createdAt or similar
            );
            when(authService.login(request)).thenReturn(response);

            ResponseEntity<LoginResponse> result = authController.login(request);

            assertEquals(200, result.getStatusCodeValue());
            assertEquals(response, result.getBody());
            verify(authService, times(1)).login(request);
        }
        }