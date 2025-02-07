package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.request.UserCreationRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.UserFullInformationResponseDTO;
import com.fpt.capstone.tourism.dto.response.UserManageGeneralInformationResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.IHelper.JwtHelper;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.UserCreationMapper;
import com.fpt.capstone.tourism.mapper.UserFullInformationMapper;
import com.fpt.capstone.tourism.mapper.UserManageGeneralInformationMapper;
import com.fpt.capstone.tourism.model.Token;
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
import org.springframework.http.HttpStatus;
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
    public GeneralResponse<?> getUserById(Long id) {
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
    @Transactional
    public GeneralResponse<?> createUser(UserCreationRequestDTO userDTO) {
        try {
            // Validate user input
            Validator.validateUserCreation(userDTO.getFullName(), userDTO.getUsername(),
                    userDTO.getPassword(), userDTO.getRePassword(), userDTO.getEmail(), String.valueOf(userDTO.getGender()),
                    userDTO.getPhone(), userDTO.getAddress(), userDTO.getAvatarImage(), userDTO.getRoleNames());

            // Check for duplicate username
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw BusinessException.of(HttpStatus.CONFLICT,DUPLICATE_USERNAME_MESSAGE);
            }
            //Check for duplicate email
            if(userRepository.existsByEmail(userDTO.getEmail())){
                throw BusinessException.of(HttpStatus.CONFLICT,EMAIL_ALREADY_EXISTS_MESSAGE);
            }
            //Check for duplicate phone number
            if (userRepository.existsByPhone(userDTO.getPhone())) {
                throw BusinessException.of(HttpStatus.CONFLICT,PHONE_ALREADY_EXISTS_MESSAGE);
            }
            // Hash password before saving
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

            // Convert DTO to Entity
            User user = userCreationMapper.toEntity(userDTO);
            user.setPassword(encodedPassword);
            user.setDeleted(false);
            user.setEmailConfirmed(true);

            User savedUser = userRepository.save(user);

            // Assign roles
            Set<UserRole> userRoles = new HashSet<>();
            for (String roleName : userDTO.getRoleNames()) {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> BusinessException.of(HttpStatus.CONFLICT,ROLE_NOT_FOUND));
                userRoles.add(new UserRole(null, savedUser, false, role));
            }
            userRoleRepository.saveAll(userRoles);
            savedUser.setUserRoles(userRoles);

            UserManageGeneralInformationResponseDTO responseDTO = userCreationMapper.toResponseDTO(savedUser);

            return GeneralResponse.of(responseDTO, CREATE_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(CREATE_USER_FAIL_MESSAGE, e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<?> updateUser(Long id, UserCreationRequestDTO userDTO) {
        try {
            // Validate user input
            Validator.validateUserUpdate(userDTO.getFullName(), userDTO.getUsername(),
                    userDTO.getPassword(), userDTO.getRePassword(), userDTO.getEmail(), String.valueOf(userDTO.getGender()),
                    userDTO.getPhone(), userDTO.getAddress(), userDTO.getAvatarImage(), userDTO.getRoleNames());
            // Find user by ID
            User user = userRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE));

            // Check for duplicate username (except for the current user)
            Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw BusinessException.of(HttpStatus.CONFLICT, DUPLICATE_USERNAME_MESSAGE);
            }

            // Check for duplicate email (except for the current user)
            Optional<User> existingEmailUser = userRepository.findByEmail(userDTO.getEmail());
            if (existingEmailUser.isPresent() && !existingEmailUser.get().getId().equals(id)) {
                throw BusinessException.of(HttpStatus.CONFLICT, EMAIL_ALREADY_EXISTS_MESSAGE);
            }

            // Check for duplicate phone number (except for the current user)
            Optional<User> existingPhoneUser = userRepository.findByPhone(userDTO.getPhone());
            if (existingPhoneUser.isPresent() && !existingPhoneUser.get().getId().equals(id)) {
                throw BusinessException.of(HttpStatus.CONFLICT, PHONE_ALREADY_EXISTS_MESSAGE);
            }

            //Only update fields if they have changed
            if (!user.getFullName().equals(userDTO.getFullName())) {
                user.setFullName(userDTO.getFullName());
            }
            if (!user.getEmail().equals(userDTO.getEmail())) {
                user.setEmail(userDTO.getEmail());
            }
            if (!user.getPhone().equals(userDTO.getPhone())) {
                user.setPhone(userDTO.getPhone());
            }
            if (!user.getAddress().equals(userDTO.getAddress())) {
                user.setAddress(userDTO.getAddress());
            }
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()
                    && !passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            //Only update roles if they have changed
            Set<String> currentRoleNames = user.getUserRoles().stream()
                    .map(userRole -> userRole.getRole().getRoleName())
                    .collect(Collectors.toSet());

            Set<String> newRoleNames = new HashSet<>(userDTO.getRoleNames());

            if (!currentRoleNames.equals(newRoleNames)) {
                Set<UserRole> newRoles = new HashSet<>();
                for (String roleName : userDTO.getRoleNames()) {
                    Role role = roleRepository.findByRoleName(roleName)
                            .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND, ROLE_NOT_FOUND));
                    newRoles.add(new UserRole(null, user, false, role));
                }
                userRoleRepository.deleteByUserId(user.getId());
                userRoleRepository.saveAll(newRoles);
                user.setUserRoles(newRoles);
            }
            // Convert to response DTO
            UserManageGeneralInformationResponseDTO responseDTO = userCreationMapper.toResponseDTO(userRepository.save(user));
            return GeneralResponse.of(responseDTO, UPDATE_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(HttpStatus.INTERNAL_SERVER_ERROR, UPDATE_USER_FAIL_MESSAGE, e);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<?> deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> BusinessException.of(HttpStatus.NOT_FOUND,USER_NOT_FOUND_MESSAGE));
            if (!user.isDeleted()) {
                user.setDeleted(true);
                userRepository.saveAndFlush(user);
            }
            return GeneralResponse.of(DELETE_USER_SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.of(DELETE_USER_FAIL_MESSAGE, e);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<UserManageGeneralInformationResponseDTO>>> getAllUser(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userRepository.findAll(pageable);
            List<UserManageGeneralInformationResponseDTO> users = userPage.getContent().stream()
                    .map(user -> {
                        List<Role> roles = roleRepository.findRolesByUserId(user.getId());
                        UserManageGeneralInformationResponseDTO userDTO = userManageGeneralInformationMapper.toDTO(user);
                        // Map role names to DTO
                        userDTO.setRoles(roles.stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toList()));
                        return userDTO;
                    })
                    .collect(Collectors.toList());
            PagingDTO<List<UserManageGeneralInformationResponseDTO>> pagingDTO = PagingDTO.<List<UserManageGeneralInformationResponseDTO>>builder()
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
