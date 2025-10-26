package com.foodapp.userinfo.service;

import com.foodapp.userinfo.dto.UserDTO;
import com.foodapp.userinfo.entity.User;
import com.foodapp.userinfo.mapper.UserMapper;
import com.foodapp.userinfo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public List<UserDTO> findAllUsers(){
        List<User> users=userRepo.findAll();
        return users.stream().map(userMapper::mapUserToUserDTO).collect(Collectors.toList());
    }

    public ResponseEntity<UserDTO> findUserById(int userId){
        Optional<User> user=userRepo.findById(userId);
        return user.map(value -> new ResponseEntity<>(userMapper.mapUserToUserDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    public UserDTO saveUser(UserDTO userDTO){
        User savedUserToDB=userRepo.save(userMapper.mapUserDTOToUser(userDTO));
        return userMapper.mapUserToUserDTO(savedUserToDB);
    }
}
