package com.foodapp.userinfo.service;

import com.foodapp.userinfo.dto.UserDTO;
import com.foodapp.userinfo.entity.User;
import com.foodapp.userinfo.mapper.UserMapper;
import com.foodapp.userinfo.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllUsers(){
        List<User> mockUsers= Arrays.asList(
                new User(1,"User 1","Password 1","Address 1","City 1"),
                new User(2,"User 2","Password 2","Address 2","City 2"),
                new User(3,"User 3","Password 3","Address 3","City 3")
        );

        when(userRepo.findAll()).thenReturn(mockUsers);

        // Mock the mapper behavior — this is critical
        when(userMapper.mapUserToUserDTO(any(User.class)))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    return new UserDTO(u.getUserId(), u.getUserName(), u.getUserPassword(), u.getUserAddress(), u.getUserCity());
                });
        List<UserDTO> response=userService.findAllUsers();


        // Assert
        assertEquals(mockUsers.size(), response.size());
        assertEquals("Password 1", response.get(0).getUserPassword());
        assertEquals("User 2", response.get(1).getUserName());
        assertEquals("Address 3", response.get(2).getUserAddress());

        // Verify interactions
        verify(userRepo, times(1)).findAll();
        verify(userMapper, times(mockUsers.size())).mapUserToUserDTO(any(User.class));
    }

    @Test
    public void testSaveUser(){
        UserDTO mockUserDTO=new UserDTO(1,"User 1","Password 1","Address 1","City 1");
        User mockUser= new User(1,"User 1","Password 1","Address 1","City 1");

        // Mock mapper behavior
        when(userMapper.mapUserDTOToUser(mockUserDTO)).thenReturn(mockUser);
        when(userMapper.mapUserToUserDTO(mockUser)).thenReturn(mockUserDTO);

        //Mock the repository behaviour
        when(userRepo.save(mockUser)).thenReturn(mockUser);

        //call the service method
        UserDTO savedUserDTO= userService.saveUser(mockUserDTO);

        //verify the result
        assertEquals(mockUserDTO,savedUserDTO);

        //verify that the repository method was called
        verify(userRepo,times(1)).save(mockUser);
        verify(userMapper, times(1)).mapUserDTOToUser(mockUserDTO);
        verify(userMapper, times(1)).mapUserToUserDTO(mockUser);
    }

    @Test
    public void testFindUserById_Id_Exist(){
        //create a mock user id
        int mockUserId=1;

        //create a mock user to be returned by the repository
        User mockUser= new User(1,"User 1","Password 1","Address 1","City 1");
        UserDTO mockUserDTO=new UserDTO(1,"User 1","Password 1","Address 1","City 1");

        //mock the repository and mapper behavior
        when(userRepo.findById(mockUserId)).thenReturn(Optional.of(mockUser));
        when(userMapper.mapUserToUserDTO(mockUser)).thenReturn(mockUserDTO);
        //call the service method
        ResponseEntity<UserDTO> response=userService.findUserById(mockUserId);

        //verify the response
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(mockUserId,response.getBody().getUserId());

        //verify that the repository method was called
        verify(userRepo,times(1)).findById(mockUserId);
        verify(userMapper, times(1)).mapUserToUserDTO(mockUser);
    }

    @Test
    public void testFindUserById_Id_Not_Exist(){
        //create a mock user id
        int mockUserId=1;


        //mock the repository and mapper behavior
        when(userRepo.findById(mockUserId)).thenReturn(Optional.empty());
        //call the service method
        ResponseEntity<UserDTO> response=userService.findUserById(mockUserId);

        //verify the response
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody());

        //verify that the repository method was called
        verify(userRepo,times(1)).findById(mockUserId);
    }
}
