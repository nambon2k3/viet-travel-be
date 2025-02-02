package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.converter.Converter;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.response.UserInfoResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.model.EmailConfirmationToken;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.EmailConfirmationTokenRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.FAIL_TO_SAVE_USER_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

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
    @Transactional
    public void createEmailConfirmationToken(User user, String token) {
        // First, delete any existing tokens for this user
        emailConfirmationTokenRepository.deleteByUser(user);

        // Create and save the new token
        EmailConfirmationToken confirmationToken = new EmailConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setUser(user);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        emailConfirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public User findUserByEmailConfirmationToken(String token) {
        Optional<EmailConfirmationToken> confirmationToken = emailConfirmationTokenRepository.findByToken(token);
        return confirmationToken.map(EmailConfirmationToken::getUser).orElse(null);
    }

    @Override
    @Transactional
    public void deleteEmailConfirmationToken(String token) {
        emailConfirmationTokenRepository.deleteByToken(token);
    }

    @Override
    public GeneralResponse<UserInfoResponseDTO> getUserProfile(String token) {
        String jwt = token.substring(7);

        String username = jwtHelper.extractUsername(jwt);

        //Find corresponding user object in database
        User user = userRepository.findByUsername(username).get();

        return GeneralResponse.of(Converter.convertUseToUserResponseDTO(user));
    }


}
