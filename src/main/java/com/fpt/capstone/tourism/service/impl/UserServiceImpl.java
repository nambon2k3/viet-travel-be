package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.UserFullInformationResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserManageGeneralInformationDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.helper.validator.UserCreationValidator;
import com.fpt.capstone.tourism.mapper.UserCreationMapper;
import com.fpt.capstone.tourism.mapper.UserFullInformationMapper;
import com.fpt.capstone.tourism.mapper.UserManageGeneralInformationMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final UserManageGeneralInformationMapper userManageGeneralInformationMapper;
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
    public GeneralResponse<?> getUserById(int id) {
        try {
            UserFullInformationResponseDTO response = userRepository.findById(id)
                    .map(user -> {
                        // Fetch all roles of the user
                        List<Role> roles = roleRepository.findRolesByUserId(user.getId());

                        UserFullInformationResponseDTO userDTO = userFullInformationMapper.toDTO(user);
                        userDTO.setRoles(roles.stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toList()));

                        return userDTO;
                    })
                    .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));
            return GeneralResponse.of(response, GET_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            return GeneralResponse.of(e);
        } catch (Exception e) {
            throw BusinessException.of(GET_USER_FAIL_MESSAGE, e);
        }
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
    @Transactional
    public GeneralResponse<?> createUser(UserCreationRequestDTO userDTO) {
        try {
            // Validate user input
            UserCreationValidator.validateUserCreation(userDTO.getFullName(), userDTO.getUsername(),
                    userDTO.getPassword(), userDTO.getRePassword(), userDTO.getEmail(), String.valueOf(userDTO.getGender()),
                    userDTO.getPhone(), userDTO.getAddress(), userDTO.getAvatarImage(), userDTO.getRoleNames());

            // Check for duplicate username
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
                throw BusinessException.of(DUPLICATE_USERNAME_MESSAGE);
            }

            // Hash password before saving
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

            // Convert DTO to Entity
            User user = userCreationMapper.toEntity(userDTO);
            user.setPassword(encodedPassword);
            user.setDeleted(false);

            User savedUser = userRepository.save(user);

            // Assign roles
            Set<UserRole> userRoles = new HashSet<>();
            for (String roleName : userDTO.getRoleNames()) {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> BusinessException.of(ROLE_NOT_FOUND));
                userRoles.add(new UserRole(null, savedUser, false, role));
            }
            userRoleRepository.saveAll(userRoles);

            return GeneralResponse.of(userCreationMapper.toDTO(savedUser), CREATE_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(CREATE_USER_FAIL_MESSAGE, e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<?> updateUser(int id, UserCreationRequestDTO userDTO) {
        try {
            // Validate user input
            UserCreationValidator.validateUserUpdate(userDTO.getFullName(), userDTO.getPhone(),
                    userDTO.getAddress(), userDTO.getEmail(), userDTO.getPassword());

            // Find user by ID
            User user = userRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));

            // Check for duplicate username (except for the current user)
            Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw BusinessException.of(DUPLICATE_USERNAME_MESSAGE);
            }

            // Update user details
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());

            // If password is provided, hash and update it
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            // Update roles
            Set<UserRole> newRoles = new HashSet<>();
            for (String roleName : userDTO.getRoleNames()) {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> BusinessException.of(ROLE_NOT_FOUND));
                newRoles.add(new UserRole(null, user, false, role));
            }

            // Clear and update roles
            userRoleRepository.deleteByUserId(user.getId());
            userRoleRepository.saveAll(newRoles);

            return GeneralResponse.of(userCreationMapper.toDTO(userRepository.save(user)), UPDATE_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(UPDATE_USER_FAIL_MESSAGE, e);
        }
    }



    @Override
    @Transactional
    public GeneralResponse<?> deleteUser(int id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(USER_NOT_FOUND_MESSAGE));
            if (!user.isDeleted()) {
                user.setDeleted(true);
                userRepository.saveAndFlush(user);
            }
            return GeneralResponse.of(null, DELETE_USER_SUCCESS_MESSAGE);
        } catch (Exception e) {
            throw BusinessException.of(DELETE_USER_FAIL_MESSAGE, e);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<UserManageGeneralInformationDTO>>> getAllUser(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userRepository.findAll(pageable);
            List<UserManageGeneralInformationDTO> users = userPage.getContent().stream()
                    .map(user -> {
                        List<Role> roles = roleRepository.findRolesByUserId(user.getId());
                        UserManageGeneralInformationDTO userDTO = userManageGeneralInformationMapper.toDTO(user);
                        // Map role names to DTO
                        userDTO.setRoles(roles.stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toList()));
                        return userDTO;
                    })
                    .collect(Collectors.toList());
            PagingDTO<List<UserManageGeneralInformationDTO>> pagingDTO = PagingDTO.<List<UserManageGeneralInformationDTO>>builder()
                    .page(page)
                    .size(size)
                    .total(userPage.getTotalElements())
                    .items(users)
                    .build();
            return GeneralResponse.of(pagingDTO, GET_ALL_USER_SUCCESS_MESSAGE);
        } catch (Exception e) {
            throw BusinessException.of(GET_ALL_USER_FAIL_MESSAGE,e);
        }
    }
}
