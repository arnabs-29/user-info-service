package com.foodapp.userinfo.controller;

import com.foodapp.userinfo.dto.UserDTO;
import com.foodapp.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> fetchAllUsers(){
        List<UserDTO> listUsers= userService.findAllUsers();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/fetchById/{id}")
    public ResponseEntity<UserDTO> fetchUserById(@PathVariable("id") Integer userId ){
        return userService.findUserById(userId);
    }
    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        UserDTO savedUser= userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
