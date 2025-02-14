package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TokenDTO;
import com.fpt.capstone.tourism.dto.common.UserDTO;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.enums.Gender;
import com.fpt.capstone.tourism.enums.RoleName;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.helper.TokenEncryptorImpl;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.model.Role;
import com.fpt.capstone.tourism.model.Token;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.RoleRepository;
import com.fpt.capstone.tourism.repository.UserRoleRepository;
import com.fpt.capstone.tourism.service.EmailConfirmationService;
import com.fpt.capstone.tourism.service.UserService;
import org.junit.jupiter.api.*;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
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
                .deleted(false).build();

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
    @Order(2)
    void testLogin_InvalidField() {
        // Arrange
        UserDTO userDTO = new UserDTO("", "password123");

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    @Order(3)
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
    @Order(4)
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
                .deleted(true).build();

        when(userService.findUserByUsername(userDTO.getUsername()))
                .thenReturn(user);

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(HttpStatus.FORBIDDEN.toString(), exception.getMessage());
    }

    @Test
    @Order(5)
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
                .deleted(false).build();

        when(userService.findUserByUsername(userDTO.getUsername()))
                .thenReturn(user);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.login(userDTO));
        assertEquals(Constants.Message.LOGIN_FAIL_MESSAGE, exception.getMessage());
    }
    @Test
    @Order(6)
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

    @Test
    @Order(7)
    void testRegister_Success() throws Exception {
        // Arrange
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .username("testUser123")
                .fullName("Test User")
                .gender(Gender.FEMALE)
                .password("password")
                .rePassword("password")
                .address("address")
                .phone("0987654321")
                .email("testuser123@gmail.com")
                .build();
        Role customerRole = Role.builder().roleName("CUSTOMER").isDeleted(false).build();
        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .fullName(registerRequestDTO.getFullName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .gender(registerRequestDTO.getGender())
                .phone(registerRequestDTO.getPhone())
                .address(registerRequestDTO.getAddress())
                .deleted(false)
                .emailConfirmed(false)
                .build();

        when(userService.existsByUsername(registerRequestDTO.getUsername())).thenReturn(false);
        when(userService.exitsByEmail(registerRequestDTO.getEmail())).thenReturn(false);
        when(userService.existsByPhoneNumber(registerRequestDTO.getPhone())).thenReturn(false);
        when(roleRepository.findByRoleName("CUSTOMER")).thenReturn(Optional.of(customerRole));
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(emailConfirmationService.createEmailConfirmationToken(user)).thenReturn(new Token());
        doNothing().when(emailConfirmationService).sendConfirmationEmail(any(User.class), any(Token.class));

        // Act
        GeneralResponse<UserInfoResponseDTO> response = authService.register(registerRequestDTO);

        // Assert
        assertEquals(Constants.Message.EMAIL_CONFIRMATION_REQUEST_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        assertEquals("testUser123", response.getData().getUsername());
        assertEquals("testuser123@gmail.com", response.getData().getEmail());
        assertEquals(RoleName.CUSTOMER, response.getData().getRole());
    }

    @Test
    @Order(8)
    void testRegister_InvalidField() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .username("")
                .fullName("Test User")
                .gender(Gender.FEMALE)
                .password("password")
                .rePassword("password")
                .address("address")
                .phone("0987654321")
                .email("testuser123@gmail.com")
                .build();

        // Act & Assert
        Exception exception = assertThrows(BusinessException.class, () -> authService.register(registerRequestDTO));
        assertEquals(Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    @Order(9)
    void testRegister_UsernameAlreadyExists() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .username("testUser123")
                .fullName("Test User")
                .gender(Gender.FEMALE)
                .password("password")
                .rePassword("password")
                .address("address")
                .phone("0987654321")
                .email("testuser123@gmail.com")
                .build();

        when(userService.existsByUsername(anyString())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(registerRequestDTO));
        assertEquals(Constants.UserExceptionInformation.USERNAME_ALREADY_EXISTS_MESSAGE, exception.getMessage());
    }

    @Test
    @Order(10)
    void testRegister_EmailAlreadyExists() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .username("testUser123")
                .fullName("Test User")
                .gender(Gender.FEMALE)
                .password("password")
                .rePassword("password")
                .address("address")
                .phone("0987654321")
                .email("testuser123@gmail.com")
                .build();

        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.exitsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(registerRequestDTO));
        assertEquals(Constants.UserExceptionInformation.EMAIL_ALREADY_EXISTS_MESSAGE, exception.getMessage());
    }
    @Test
    @Order(11)
    void testRegister_PhoneNumberAlreadyExists() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .username("testUser123")
                .fullName("Test User")
                .gender(Gender.FEMALE)
                .password("password")
                .rePassword("password")
                .address("address")
                .phone("0987654321")
                .email("testuser123@gmail.com")
                .build();

        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.exitsByEmail(anyString())).thenReturn(false);
        when(userService.existsByPhoneNumber(anyString())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(registerRequestDTO));
        assertEquals(Constants.UserExceptionInformation.PHONE_ALREADY_EXISTS_MESSAGE, exception.getMessage());
    }

    @Test
    @Order(12)
    void testRegister_PasswordMismatch() {
        // Arrange
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .username("testUser123")
                .fullName("Test User")
                .gender(Gender.FEMALE)
                .password("password")
                .rePassword("rePassword")
                .address("address")
                .phone("0987654321")
                .email("testuser123@gmail.com")
                .build();

        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.exitsByEmail(anyString())).thenReturn(false);
        when(userService.existsByPhoneNumber(anyString())).thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(registerRequestDTO));
        assertEquals(Constants.Message.PASSWORDS_DO_NOT_MATCH_MESSAGE, exception.getMessage());
    }
    @Test
    @Order(13)
    void testConfirmEmail_Success() {
        // Arrange
        String validToken = "valid-token";
        User user = User.builder()
                .id(1L)
                .username("testUser123")
                .email("testUser123@example.com")
                .emailConfirmed(false)
                .build();

        Token token = Token.builder()
                .token(validToken)
                .user(user)
                .build();

        when(emailConfirmationService.validateConfirmationToken(validToken)).thenReturn(token);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Act
        GeneralResponse<String> response = authService.confirmEmail(validToken);

        // Assert
        assertEquals(Constants.Message.EMAIL_CONFIRMED_SUCCESS_MESSAGE, response.getMessage());
        assertNull(response.getData());
        assertTrue(user.isEmailConfirmed());
        verify(emailConfirmationService, times(1)).validateConfirmationToken(validToken);
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    @Order(14)
    void testConfirmEmail_InvalidToken() {
        // Arrange
        String invalidToken = "invalid-token";
        when(emailConfirmationService.validateConfirmationToken(invalidToken))
                .thenThrow(BusinessException.of(CONFIRM_EMAIL_FAILED));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.confirmEmail(invalidToken));
        assertEquals(CONFIRM_EMAIL_FAILED, exception.getMessage());
        verify(emailConfirmationService, times(1)).validateConfirmationToken(invalidToken);
        verify(userService, never()).saveUser(any(User.class));
    }
}
