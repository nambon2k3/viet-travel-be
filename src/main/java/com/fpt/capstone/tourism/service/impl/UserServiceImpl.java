package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserProfileRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserProfileResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.model.Token;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.EmailConfirmationTokenRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.CloudinaryService;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.FAIL_TO_SAVE_USER_MESSAGE;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @Override
    public String generateToken(User user) {
        return jwtHelper.generateToken(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findUserById(id).orElseThrow();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Transactional
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
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
    @Transactional
    public void createEmailConfirmationToken(User user, String token) {
        // First, delete any existing tokens for this user
        emailConfirmationTokenRepository.deleteByUser(user);
        // Create and save the new token
        Token confirmationToken = new Token();
        confirmationToken.setToken(token);
        confirmationToken.setUser(user);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        emailConfirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public User findUserByEmailConfirmationToken(String token) {
        Optional<Token> confirmationToken = emailConfirmationTokenRepository.findByToken(token);
        return confirmationToken.map(Token::getUser).orElse(null);
    }

    @Override
    @Transactional
    public void deleteEmailConfirmationToken(String token) {
        emailConfirmationTokenRepository.deleteByToken(token);
    }

    @Override
    public GeneralResponse<UserProfileResponseDTO> getUserProfile(String token) {
        try {
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
                    .avatarImg(currentUser.getAvatarImage())
                    .build();

            return GeneralResponse.of(userProfileResponseDTO, GET_PROFILE_SUCCESS);
        } catch (Exception e){
            throw BusinessException.of(GET_PROFILE_FAIL);
        }
    }

    @Override
    public GeneralResponse<UserProfileResponseDTO> updateUserProfile(String token, Long userId, UserProfileRequestDTO newUser) {

        try{
            String jwt = token.substring(7);

            String username = jwtHelper.extractUsername(jwt);

            User currentUser = findUserByUsername(username);

            if(!userId.equals(currentUser.getId())){
                throw BusinessException.of(HttpStatus.FORBIDDEN.toString());

            }

            User existingUser = findById(userId);

            //Check valid users field need to update
            Validator.isProfileValid(newUser.getFullName(), newUser.getEmail(),
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
            return GeneralResponse.of(userProfileResponseDTO, UPDATE_PROFILE_SUCCESS);
        } catch (Exception ex){
            throw BusinessException.of(UPDATE_PROFILE_FAIL, ex);
        }


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
    public GeneralResponse<String> changePassword(String token, String currentPassword, String newPassword, String newRePassword) {
        try{
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

            return new GeneralResponse<>(HttpStatus.OK.value(), CHANGE_PASSWORD_SUCCESS_MESSAGE, token);
        } catch (Exception e){
            throw BusinessException.of(Constants.Message.CHANGE_PASSWORD_FAIL_MESSAGE);
        }
    }

    @Override
    public GeneralResponse<String> updateAvatar(Long userId, MultipartFile file) {
        try {
            User user = findById(userId);

            String imageURL = cloudinaryService.uploadAvatar(file, userId);

            //Update imageURL in database
            user.setAvatarImage(imageURL);
            userRepository.save(user);
            return new GeneralResponse<>(HttpStatus.OK.value(), UPDATE_AVATAR_SUCCESS, imageURL);
        } catch (Exception ex){
            throw BusinessException.of(UPDATE_AVATAR_FAIL, ex);
        }
    }
}
