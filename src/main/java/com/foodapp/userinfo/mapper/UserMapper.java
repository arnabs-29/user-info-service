package com.foodapp.userinfo.mapper;

import com.foodapp.userinfo.dto.UserDTO;
import com.foodapp.userinfo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapUserDTOToUser(UserDTO userDTO);
    UserDTO mapUserToUserDTO(User user);
}
