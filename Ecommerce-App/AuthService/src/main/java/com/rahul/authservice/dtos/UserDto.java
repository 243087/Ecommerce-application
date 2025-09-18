package com.rahul.authservice.dtos;

import com.rahul.authservice.models.Role;
import com.rahul.authservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private List<Role> roles;

    // below method is used in controller for converting the user to userDTO
    public static UserDto from(User user){
        if(user==null)
            return null;

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
