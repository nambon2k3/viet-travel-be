package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TokenDTO;
import com.fpt.capstone.tourism.dto.common.UserDTO;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.helper.TokenEncryptorImpl;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.model.Role;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.RoleRepository;
import com.fpt.capstone.tourism.repository.UserRoleRepository;
import com.fpt.capstone.tourism.service.EmailConfirmationService;
import com.fpt.capstone.tourism.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailConfirmationService emailConfirmationService;

    @Mock
    private TokenEncryptorImpl tokenEncryptor;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    // Test case for successful login
    @Test
    void testLogin_Success() {
        // Arrange
        UserDTO userDTO = new UserDTO("LanAnh99", "password123");
        User user = User.builder()
                .fullName("Lan Than")
                .username("LanAnh99")
                .email("lananh99@gmail.com")
                .gender(Gender.FEMALE)
                .password("password123")
                .phone("0987654321")
                .address("BG")
                .avatarImage(null)
                .emailConfirmed(true)
                .isDeleted(false).build();

        String token = "jwt-token";

        Authentication authentication = mock(Authentication.class);

        // Mocking the behavior
        when(userService.findUserByUsername(userDTO.getUsername())).thenReturn(user);
        when(jwtHelper.generateToken(user)).thenReturn(token);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Act
        GeneralResponse<TokenDTO> response = authService.login(userDTO);

        // Assert
        assertEquals(Constants.Message.LOGIN_SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(token, response.getData().getToken());
    }

    @Test
    void testLogin_InvalidField() {
        // Arrange
        UserDTO userDTO = new UserDTO("", "password123");

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        UserDTO userDTO = new UserDTO("nonExistentUser", "password123");

        when(userService.findUserByUsername(userDTO.getUsername()))
                .thenThrow(new NoSuchElementException()); // User not found

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(LOGIN_FAIL_MESSAGE, exception.getMessage());
    }
    @Test
    void testLogin_UserIsDeleted() {
        // Arrange
        UserDTO userDTO = new UserDTO("isDeletedUser123", "password123");
        User user = User.builder()
                .fullName("Lan Than")
                .username("isDeletedUser123")
                .email("lananh99@gmail.com")
                .gender(Gender.FEMALE)
                .password("password123")
                .phone("0987654321")
                .address("BG")
                .avatarImage(null)
                .emailConfirmed(true)
                .isDeleted(true).build();

        when(userService.findUserByUsername(userDTO.getUsername()))
                .thenReturn(user);

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(HttpStatus.FORBIDDEN.toString(), exception.getMessage());
    }

    @Test
    void testLogin_EmailNotConfirmed() {
        // Arrange
        UserDTO userDTO = new UserDTO("testUser123", "password123");
        User user = User.builder()
                .fullName("Lan Than")
                .username("testUser123")
                .email("testUser123@gmail.com") //Email is not confirmed
                .gender(Gender.FEMALE)
                .password("password123")
                .phone("0987654321")
                .address("BG")
                .avatarImage(null)
                .emailConfirmed(false)
                .isDeleted(false).build();

        when(userService.findUserByUsername(userDTO.getUsername()))
                .thenReturn(user);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(Constants.Message.LOGIN_FAIL_MESSAGE, exception.getMessage());
    }
    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        UserDTO userDTO = new UserDTO("testUser123", "wrongPassword");

        // Mocking authentication failure
        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(Constants.Message.LOGIN_FAIL_MESSAGE, exception.getMessage());
    }

//    @Test
//    void testRegister_Success() {
//        // Arrange
//        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO("testUser", "password123", "password123", "Full Name", "1234567890", "123 Street", "test@example.com");
//        Role userRole = new Role("CUSTOMER");
//        User user = new User("testUser", "password123", "test@example.com", false);
//
//        when(userService.existsByUsername(registerRequestDTO.getUsername())).thenReturn(false);
//        when(userService.exitsByEmail(registerRequestDTO.getEmail())).thenReturn(false);
//        when(userService.existsByPhoneNumber(registerRequestDTO.getPhone())).thenReturn(false);
//        when(roleRepository.findByRoleName("CUSTOMER")).thenReturn(Optional.of(userRole));
//        when(userService.saveUser(any(User.class))).thenReturn(user);
//        when(emailConfirmationService.createEmailConfirmationToken(user)).thenReturn(new EmailConfirmationToken());
//        doNothing().when(emailConfirmationService).sendConfirmationEmail(any(User.class), any(EmailConfirmationToken.class));
//
//        // Act
//        GeneralResponse<UserInfoResponseDTO> response = authService.register(registerRequestDTO);
//
//        // Assert
//        assertEquals(Constants.Message.EMAIL_CONFIRMATION_REQUEST_MESSAGE, response.getMessage());
//        assertNotNull(response.getData());
//    }
}
