package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.mapper.UserCreationMapper;
import com.fpt.capstone.tourism.model.EmailConfirmationToken;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.EmailConfirmationTokenRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final UserCreationMapper userCreationMapper;

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
    public GeneralResponse<?> getUserById(int id) {
        try {
            UserCreationRequestDTO response = userRepository.findById(id)
                    .map(userCreationMapper::toDTO)
                    .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));
            return GeneralResponse.of(response, GET_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            return GeneralResponse.of(e);
        } catch (Exception e) {
            return GeneralResponse.of(e, GET_USER_FAIL_MESSAGE);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<?> createUser(UserCreationRequestDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());

        if (existingUser.isPresent()) {
            throw BusinessException.of(DUPLICATE_USERNAME_MESSAGE);
        }

        User user = userCreationMapper.toEntity(userDTO);
        user.setDeleted(false);
        return GeneralResponse.of(userCreationMapper.toDTO(userRepository.save(user)), CREATE_USER_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional
    public GeneralResponse<?> updateUser(int id, UserCreationRequestDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));

        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw BusinessException.of(DUPLICATE_USERNAME_MESSAGE);
        }
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole());

        return GeneralResponse.of(userCreationMapper.toDTO(userRepository.save(user)), UPDATE_USER_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional
    public GeneralResponse<?> deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));
        if (!user.isDeleted()) {
            user.setDeleted(true);
            userRepository.saveAndFlush(user);
        }
        return GeneralResponse.of(null, DELETE_USER_SUCCESS_MESSAGE);
    }
}
