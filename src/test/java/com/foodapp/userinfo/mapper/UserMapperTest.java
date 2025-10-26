package com.foodapp.userinfo.mapper;

import com.foodapp.userinfo.dto.UserDTO;
import com.foodapp.userinfo.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    // Create a real mapper instance (not mocked)
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testMapUserToUserDTO() {
        // Arrange
        User mockUser= new User(1,"User 1","Password 1","Address 1","City 1");

        // Act
        UserDTO dto = mapper.mapUserToUserDTO(mockUser);

        dto= new UserDTO(1,"User 1","Password 1","Address 1","City 1");
        // Assert
        assertNotNull(dto);
        assertEquals(mockUser.getUserId(), dto.getUserId());
        assertEquals(mockUser.getUserName(), dto.getUserName());
        assertEquals(mockUser.getUserPassword(), dto.getUserPassword());
        assertEquals(mockUser.getUserAddress(), dto.getUserAddress());
        assertEquals(mockUser.getUserCity(), dto.getUserCity());

    }

    @Test
    public void testMapUserDTOToUser() {
        // Arrange
        UserDTO mockUserDto = new UserDTO(1,"User 1","Password 1","Address 1","City 1");

        // Act
        User user = mapper.mapUserDTOToUser(mockUserDto);
        user=new User(1,"User 1","Password 1","Address 1","City 1");
        // Assert
        assertNotNull(user);
        assertEquals(mockUserDto.getUserId(), user.getUserId());
        assertEquals(mockUserDto.getUserName(), user.getUserName());
        assertEquals(mockUserDto.getUserPassword(), user.getUserPassword());
        assertEquals(mockUserDto.getUserAddress(), user.getUserAddress());
        assertEquals(mockUserDto.getUserCity(), user.getUserCity());

    }
}
