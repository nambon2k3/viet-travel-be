package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.dto.response.UserFullInformationResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.mapper.UserCreationMapper;
import com.fpt.capstone.tourism.mapper.UserFullInformationMapper;
import com.fpt.capstone.tourism.model.EmailConfirmationToken;
import com.fpt.capstone.tourism.model.Role;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.model.UserRole;
import com.fpt.capstone.tourism.repository.EmailConfirmationTokenRepository;
import com.fpt.capstone.tourism.repository.RoleRepository;
import com.fpt.capstone.tourism.repository.UserRepository;
import com.fpt.capstone.tourism.repository.UserRoleRepository;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;
import static com.fpt.capstone.tourism.constants.Constants.UserExceptionInformation.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final UserCreationMapper userCreationMapper;
    private final UserFullInformationMapper userFullInformationMapper;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
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
    @Transactional
    public void createEmailConfirmationToken(User user, String token) {
        emailConfirmationTokenRepository.deleteByUser(user);
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
            UserFullInformationResponseDTO response = userRepository.findById(id)
                    .map(userFullInformationMapper::toDTO)
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
        // Check for duplicate username
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw BusinessException.of(DUPLICATE_USERNAME_MESSAGE);
        }

        // Hash password before saving
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = userCreationMapper.toEntity(userDTO);
        user.setPassword(encodedPassword);
        user.setDeleted(false);

        User savedUser = userRepository.save(user);

        // Assign roles
        Set<UserRole> userRoles = new HashSet<>();
        for (Long roleId : userDTO.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> BusinessException.of(ROLE_NOT_FOUND));
            userRoles.add(new UserRole(null, savedUser, false, role));
        }
        userRoleRepository.saveAll(userRoles);
        return GeneralResponse.of(userCreationMapper.toDTO(savedUser), CREATE_USER_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional
    public GeneralResponse<?> updateUser(int id, UserCreationRequestDTO userDTO) {
        // Find user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));

        // Check for duplicate username (excluding the current user)
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw BusinessException.of(DUPLICATE_USERNAME_MESSAGE);
        }

        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());

        // If password is provided, hash and update it
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        // Update user roles
        Set<UserRole> newRoles = new HashSet<>();
        for (Long roleId : userDTO.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> BusinessException.of(ROLE_NOT_FOUND));
            newRoles.add(new UserRole(null, user, false, role));
        }

        // Clear and update roles
        userRoleRepository.deleteByUserId(user.getId());
        userRoleRepository.saveAll(newRoles);

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
