package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.TokenDTO;
import com.fpt.capstone.tourism.dto.common.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.LoginRequestDTO;
import com.fpt.capstone.tourism.dto.request.RegisterConfirmRequestDTO;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.RegisterInforResponseDTO;
import com.fpt.capstone.tourism.dto.response.RegisterInitResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.helper.validator.*;
import com.fpt.capstone.tourism.model.Role;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.model.UserRole;
import com.fpt.capstone.tourism.repository.RoleRepository;
import com.fpt.capstone.tourism.repository.UserRoleRepository;
import com.fpt.capstone.tourism.service.AuthService;
import com.fpt.capstone.tourism.service.EmailConfirmationService;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailConfirmationService emailConfirmationService;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public GeneralResponse<TokenDTO> login(LoginRequestDTO userDTO) {
        Validator.isFieldValid(userDTO.getUsername(), Validator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);
        Validator.isFieldValid(userDTO.getPassword(), Validator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getUsername(), userDTO.getPassword()
            ));
            User user = userService.findUserByUsername(userDTO.getUsername());

            // Check if the user's email is confirmed
            if (!user.isEmailConfirmed()) {
                throw BusinessException.of(Constants.Message.LOGIN_FAIL_MESSAGE);
            }
            String token = jwtHelper.generateToken(user);
            TokenDTO tokenDTO = TokenDTO.builder()
                    .username(user.getUsername())
                    .token(token)
                    .expirationTime("24h")
                    .build();


            return new GeneralResponse<>(HttpStatus.OK.value(), Constants.Message.LOGIN_SUCCESS_MESSAGE, tokenDTO);
        } catch (BusinessException be) {
            throw be;
        }catch (Exception ex) {
            throw BusinessException.of(Constants.Message.LOGIN_FAIL_MESSAGE, ex);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<RegisterInitResponseDTO> registerInit(RegisterRequestDTO registerRequestDTO) {
        // Validate input data
        if (!RegisterValidator.isRegisterValid(
                registerRequestDTO.getUsername(),
                registerRequestDTO.getPassword(),
                registerRequestDTO.getRePassword(),
                registerRequestDTO.getFullName(),
                registerRequestDTO.getPhone(),
                registerRequestDTO.getAddress(),
                registerRequestDTO.getEmail())) {
            throw BusinessException.of(Constants.Message.INVALID_REGISTER_INFO);
        }


        if (userService.existsByUsername(registerRequestDTO.getUsername())) {
            throw BusinessException.of(Constants.UserExceptionInformation.USERNAME_ALREADY_EXISTS_MESSAGE);
        }
        if (userService.exitsByEmail(registerRequestDTO.getEmail())) {
            throw BusinessException.of(Constants.UserExceptionInformation.EMAIL_ALREADY_EXISTS_MESSAGE);
        }
        if (userService.existsByPhoneNumber(registerRequestDTO.getPhone())) {
            throw BusinessException.of(Constants.UserExceptionInformation.PHONE_ALREADY_EXISTS_MESSAGE);
        }
        if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getRePassword())) {
            throw BusinessException.of(Constants.Message.PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }

        // Generate a temporary token
        String tempToken = emailConfirmationService.generateTemporaryToken();

        // Create a user object but don't save it yet
        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .fullName(registerRequestDTO.getFullName())
                .email(registerRequestDTO.getEmail())
                .gender(registerRequestDTO.getGender())
                .phone(registerRequestDTO.getPhone())
                .address(registerRequestDTO.getAddress())
                .build();

        // Send confirmation email
        try {
            emailConfirmationService.sendConfirmationEmail(user, tempToken);
        } catch (Exception e) {
            throw BusinessException.of(Constants.Message.REGISTER_FAIL_MESSAGE);
        }

        RegisterInitResponseDTO responseDTO = RegisterInitResponseDTO.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .fullName(registerRequestDTO.getFullName())
                .phone(registerRequestDTO.getPhone())
                .address(registerRequestDTO.getAddress())
                .gender(registerRequestDTO.getGender())
                .confirmationToken(tempToken)
                .build();
        return new GeneralResponse<>(HttpStatus.OK.value(), Constants.Message.EMAIL_CONFIRMATION_REQUEST_MESSAGE, responseDTO);
    }

    @Override
    @Transactional
    public GeneralResponse<RegisterInforResponseDTO> registerConfirm(RegisterConfirmRequestDTO requestDTO) {

        Role userRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseGet(() -> {
                    Role newRole = Role.builder()
                            .roleName("CUSTOMER")
                            .isDeleted(false)
                            .build();
                    return roleRepository.save(newRole);
                });

        // Create and save user
        User user = User.builder()
                .username(requestDTO.getUsername())
                .fullName(requestDTO.getFullName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .gender(requestDTO.getGender())
                .phone(requestDTO.getPhone())
                .address(requestDTO.getAddress())
                .isDeleted(false)
                .emailConfirmed(true)
                .build();

        User savedUser = userService.saveUser(user);

        // Assign role to user
        UserRole newUserRole = UserRole.builder()
                .user(savedUser)
                .role(userRole)
                .isDeleted(false)
                .build();
        userRoleRepository.save(newUserRole);

        RegisterInforResponseDTO userResponseDTO = RegisterInforResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .gender(savedUser.getGender())
                .phone(savedUser.getPhone())
                .address(savedUser.getAddress())
                .build();

        return new GeneralResponse<>(HttpStatus.CREATED.value(), Constants.Message.EMAIL_CONFIRMED_SUCCESS_MESSAGE, userResponseDTO);
    }

}