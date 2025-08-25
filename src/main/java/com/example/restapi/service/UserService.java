package com.example.restapi.service;

import java.util.List;
import java.util.Optional;

import com.example.restapi.mapper.UserMapper;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.model.User;
import com.example.restapi.mapper.UserMapper;
import com.example.restapi.dto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
/**
     * Constructor injection (recommended)
     */
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional(readOnly=true)
    public List<UserResponseDTO> getAllUsers(String name){
        List<User> users;

          if (name != null && !name.trim().isEmpty()) {
            users = userRepository.findByNameIgnoreCase(name.trim());
        } else {
            users = userRepository.findAll();
        }
        
        return UserMapper.toResponseDTOList(users);
    }


       /**
     * Get a specific user by ID
     * @param id The user ID
     * @return UserResponseDTO
     * @throws UserNotFoundException if user doesn't exist
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        
        return UserMapper.toResponseDTO(user);
    }
          /**
     * Create a new user
     * Business rule: Email must be unique
     * @param createDTO User data from request
     * @return UserResponseDTO with assigned ID
      * @throws IllegalArgumentException if email already exists
     */
    public UserResponseDTO createUser(UserCreateDTO createDTO) {
        // Business logic: Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(createDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + createDTO.getEmail());
        }
        
        // Use mapper for conversion
        User user = UserMapper.toEntity(createDTO);
        User savedUser = userRepository.save(user);
        
        return UserMapper.toResponseDTO(savedUser);
    }


     /**
     * Update an existing user
     * @param id The user ID to update
     * @param updateDTO New user data
     * @return Updated UserResponseDTO
     * @throws UserNotFoundException if user doesn't exist
     */
    public UserResponseDTO updateUser(Long id, UserCreateDTO updateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        
        // Business logic could go here
        // Example: prevent changing to existing email
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(existingUser.getEmail())) {
            Optional<User> emailUser = userRepository.findByEmail(updateDTO.getEmail());
            if (emailUser.isPresent() && !emailUser.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email already exists: " + updateDTO.getEmail());
            }
        }
          // Use mapper for update
        UserMapper.updateEntityFromDTO(updateDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);
        
        return UserMapper.toResponseDTO(updatedUser);
    }
         /**
     * Delete a user by ID
     * @param id The user ID to delete
     * @throws UserNotFoundException if user doesn't exist
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    }
    

