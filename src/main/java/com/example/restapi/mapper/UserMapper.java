package com.example.restapi.mapper;

import java.util.stream.Collectors;

import com.example.restapi.dto.UserCreateDTO;
import com.example.restapi.dto.UserResponseDTO;
import com.example.restapi.model.User;
import java.util.List;
import java.util.stream.Collector;
public class UserMapper {
       /**
     * Convert UserCreateDTO to User entity
     */
    public static User toEntity(UserCreateDTO dto){
        User user=new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        return user;
    }

    /**
     * Convert User entity to UserResponseDTO
     */
    public static UserResponseDTO toResponseDTO(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
       /**
     * Convert list of User entities to list of UserResponseDTOs
     */
    public static List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update existing User entity from UserCreateDTO
     * Used for PUT requests (partial updates)
     */
    public static void updateEntityFromDTO(UserCreateDTO dto, User entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
    }
 
}
