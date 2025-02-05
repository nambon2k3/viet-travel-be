package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.converter.Converter;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserProfileRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserProfileResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;

import com.fpt.capstone.tourism.helper.validator.UserProfileValidator;

import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.UserService;

import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.FAIL_TO_SAVE_USER_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateToken(User user) {
        return jwtHelper.generateToken(user);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findUserById(Integer.parseInt(id)).orElseThrow();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }


    @Override
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw BusinessException.of(FAIL_TO_SAVE_USER_MESSAGE,e);
        }
    }

    @Override
    public Boolean existsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public Boolean exitsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override

    public Boolean existsByPhoneNumber(String phone) {

        return userRepository.existsByPhone(phone);
    }

    @Override
    public GeneralResponse<UserProfileResponseDTO> getUserProfile(String token) {
        String jwt = token.substring(7);

        String username = jwtHelper.extractUsername(jwt);

        User currentUser = userRepository.findByUsername(username).orElseThrow();

        UserProfileResponseDTO userProfileResponseDTO = UserProfileResponseDTO.builder()
                .id(currentUser.getId())
                .username(currentUser.getUsername())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .gender(currentUser.getGender())
                .phone(currentUser.getPhone())
                .address(currentUser.getAddress())
                .build();

        return GeneralResponse.of(userProfileResponseDTO);
    }

    @Override
    public GeneralResponse<UserProfileResponseDTO> updateUserProfile(String token, Integer userId, UserProfileRequestDTO newUser) {

        String jwt = token.substring(7);

        String username = jwtHelper.extractUsername(jwt);

        User currentUser = findUserByUsername(username);

        if(!userId.equals(currentUser.getId())){
            throw BusinessException.of(HttpStatus.FORBIDDEN.toString());

        }

        User existingUser = findUserById(userId.toString());

        //Check valid users field need to update
        UserProfileValidator.isProfileValid(newUser.getFullName(), newUser.getEmail(),
                newUser.getPhone(), newUser.getAddress());

        //Update user follow by userProfileRequestDTO
        existingUser.setFullName(newUser.getFullName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setGender(newUser.getGender());
        existingUser.setPhone(newUser.getPhone());
        existingUser.setAddress(newUser.getAddress());

        userRepository.save(existingUser);

        UserProfileResponseDTO userProfileResponseDTO = UserProfileResponseDTO.builder()
                .id(existingUser.getId())
                .username(existingUser.getUsername())
                .fullName(newUser.getFullName())
                .email(newUser.getEmail())
                .gender(newUser.getGender())
                .phone(newUser.getPhone())
                .address(newUser.getAddress())
                .build();
        return GeneralResponse.of(userProfileResponseDTO, Constants.Message.USER_UPDATE_SUCCESS);

    }

    @Override
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw BusinessException.of(Constants.Message.USER_NOT_AUTHENTICATED);
        }
        System.out.println(authentication.getPrincipal().toString());
        return authentication.getPrincipal().toString();
    }

    @Override
    public String changePassword(String token, String currentPassword, String newPassword, String newRePassword) {
//        try{
            String jwt = token.substring(7);
            String username = jwtHelper.extractUsername(jwt);

            //Find user from database
            User currentUser = findUserByUsername(username);

            //Check current password correct or not
            if(!passwordEncoder.matches(currentPassword, currentUser.getPassword())){
                throw BusinessException.of(Constants.Message.PASSWORDS_INCORRECT_MESSAGE);
            }

            //Valid new password
            if(!Validator.isPasswordValid(newPassword)){
                throw BusinessException.of(Constants.UserExceptionInformation.PASSWORD_INVALID);
            }

            if(!newPassword.equals(newRePassword)){
                throw BusinessException.of(Constants.Message.PASSWORDS_DO_NOT_MATCH_MESSAGE);
            }

            currentUser.setPassword(passwordEncoder.encode(newPassword));
            saveUser(currentUser);

            return Constants.Message.CHANGE_PASSWORD_SUCCESS_MESSAGE;
//        } catch (Exception e){
//            throw BusinessException.of(Constants.Message.CHANGE_PASSWORD_FAIL_MESSAGE);
//        }
    }
}
