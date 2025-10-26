package com.foodapp.userinfo.controller;

import com.foodapp.userinfo.dto.UserDTO;
import com.foodapp.userinfo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testFetchAllUsers(){
        List<UserDTO> mockUsers=List.of(
                new UserDTO(1,"User 1","Password 1","Address 1","City 1"),
                new UserDTO(2,"User 2","Password 2","Address 2","City 2"),
                new UserDTO(3,"User 3","Password 3","Address 3","City 3")
                );

        when(userService.findAllUsers()).thenReturn(mockUsers);

        //Call the controller method
        ResponseEntity<List<UserDTO>> response= userController.fetchAllUsers();

        //Verify the response
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(mockUsers,response.getBody());

        //Verify that the service method was called
        verify(userService,times(1)).findAllUsers();
    }

    @Test
    public void testFetchUserById(){
        //Create a mock user ID
        int mockUserId = 1;

        //Create a mock user to be returned by the service
        UserDTO mockUser= new UserDTO(1,"User 1","Password 1","Address 1","City 1");

        //mock the user behaviour
        when(userService.findUserById(mockUserId)).thenReturn(new ResponseEntity<>(mockUser,HttpStatus.OK));

        ResponseEntity<UserDTO> response=userController.fetchUserById(mockUserId);
        //verify the response
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(mockUser,response.getBody());

        //Verify that the service method was called
        verify(userService,times(1)).findUserById(mockUserId);
    }

    @Test
    public void testAddUser(){
        //create a user to be saved
        UserDTO mockUser= new UserDTO(1,"User 1","Password 1","Address 1","City 1");
        //mock the service behaviour
        when(userService.saveUser(mockUser)).thenReturn(mockUser);
        //call the controller method
        ResponseEntity<UserDTO> response= userController.addUser(mockUser);
        //Verify the response
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(mockUser,response.getBody());

        //Verify that the service method was called
        verify(userService,times(1)).saveUser(mockUser);
    }
}
