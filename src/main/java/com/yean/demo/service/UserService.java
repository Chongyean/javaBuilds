package com.yean.demo.service;

import com.yean.demo.dto.UserResponseDto;
import com.yean.demo.entity.User;
import com.yean.demo.mapper.UserMapper;
import com.yean.demo.model.BaseResponseModel;
import com.yean.demo.model.BaseResponseWithDataModel;
import com.yean.demo.dto.UserDto;
import com.yean.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> dtos = mapper.toDtoList(users);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","successfully retrieve users",dtos));
    }

    public ResponseEntity<BaseResponseWithDataModel> getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseWithDataModel("fail","user not found with id : " + userId,null));
        }

        UserResponseDto dto = mapper.toDto(user.get());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","user found",dto));
    }

    public ResponseEntity<BaseResponseModel> createUser(UserDto payload) {
        User user = mapper.toEntity(payload);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created user"));
    }

    public ResponseEntity<BaseResponseModel> updateUser(UserDto payload, Long userId) {
        Optional<User> existing = userRepository.findById(userId);

        // if user not found, then response 404
        if(existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","user not found with id: " + userId));
        }

        // modify values
        User updatedUser = existing.get();
        mapper.updateEntityFromDto(updatedUser,payload);

        userRepository.save(updatedUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated user"));
    }

    public ResponseEntity<BaseResponseModel> deleteUser(Long userId) {
        // if user not found, then response 404
        if(!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","user not found with id: " + userId));
        }

        // user found, then delete
        userRepository.deleteById(userId);

        // 200 OK
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted user"));
    }
}
