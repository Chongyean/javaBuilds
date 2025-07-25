package com.yean.demo.mapper;

import com.yean.demo.dto.UserDto;
import com.yean.demo.dto.UserResponseDto;
import com.yean.demo.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        User entity = new User();

        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setAge(dto.getAge());
        entity.setRole(dto.getRole());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }

    public void updateEntityFromDto(User entity, UserDto dto) {
        if(entity == null || dto == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setAddress(dto.getAddress());
    }

    public UserResponseDto toDto(User entity) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());
        dto.setAddress(entity.getAddress());
        dto.setRole(entity.getRole());

        return dto;
    }

    public List<UserResponseDto> toDtoList(List<User> entities) {
        if(entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
