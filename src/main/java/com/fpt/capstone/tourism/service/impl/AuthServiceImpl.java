package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.TokenDTO;
import com.fpt.capstone.tourism.dto.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.RegisterRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.enums.Role;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.helper.validator.*;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.service.AuthService;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GeneralResponse<TokenDTO> login(UserDTO userDTO) {
        CommonValidator.isFieldValid(userDTO.getUsername(), CommonValidator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);
        CommonValidator.isFieldValid(userDTO.getPassword(), CommonValidator::isNullOrEmpty, Constants.UserExceptionInformation.USER_INFORMATION_NULL_OR_EMPTY);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getUsername(), userDTO.getPassword()
            ));
            User user = userService.findUserByUsername(userDTO.getUsername());
            String token = jwtHelper.generateToken(user);


            TokenDTO tokenDTO = TokenDTO.builder()
                    .username(user.getUsername())
                    .token(token)
                    .expirationTime("24h")
                    .build();

            return new GeneralResponse<>(HttpStatus.OK.value(), Constants.Message.LOGIN_SUCCESS_MESSAGE, tokenDTO);
        } catch (Exception ex) {
            throw BusinessException.of(Constants.Message.LOGIN_FAIL_MESSAGE, ex);
        }

    }

    @Override
    public GeneralResponse<UserInfoResponseDTO> register(RegisterRequestDTO registerRequestDTO) {
        if (RegisterValidator.isRegisterValid(registerRequestDTO.getUsername(),
                registerRequestDTO.getPassword(),
                registerRequestDTO.getFullName(),
                registerRequestDTO.getEmail())) {

            if (userService.existsByUsername(registerRequestDTO.getUsername())) {
                throw BusinessException.of(Constants.UserExceptionInformation.USERNAME_ALREADY_EXISTS_MESSAGE);
            }
            if(userService.exitsByEmail(registerRequestDTO.getEmail())){
                throw BusinessException.of(Constants.UserExceptionInformation.EMAIL_ALREADY_EXISTS_MESSAGE);
            }
            if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getRePassword())) {
                throw BusinessException.of(Constants.Message.PASSWORDS_DO_NOT_MATCH_MESSAGE);
            }
        }

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .email(registerRequestDTO.getEmail())
                .fullName(registerRequestDTO.getFullName())
                .role(Role.USER)
                .createdDate(LocalDateTime.now())
                .isDeleted(false)
                .build();

        User savedUser = userService.saveUser(user);

        UserInfoResponseDTO userResponseDTO = UserInfoResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .role(savedUser.getRole())
                .build();
        return new GeneralResponse<>(HttpStatus.CREATED.value(), Constants.Message.REGISTER_SUCCESS_MESSAGE, userResponseDTO);
    }
}
