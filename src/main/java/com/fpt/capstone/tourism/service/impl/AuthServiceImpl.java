package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.TokenDTO;
import com.fpt.capstone.tourism.dto.UserDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.service.AuthService;
import com.fpt.capstone.tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {


    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;

    @Override
    public GeneralResponse<TokenDTO> login(UserDTO userDTO) {

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
}
