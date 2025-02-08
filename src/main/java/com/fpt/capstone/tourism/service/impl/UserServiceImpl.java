package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.model.Token;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.EmailConfirmationTokenRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

}
