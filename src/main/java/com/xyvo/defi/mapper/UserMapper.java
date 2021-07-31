package com.xyvo.defi.mapper;

import com.xyvo.defi.domain.profile.User;
import com.xyvo.defi.dto.UserDto;
import com.xyvo.defi.validator.UserValidator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    private UserMapper(){}

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setCreatedTimestamp(new Timestamp(user.getCreated().getTime()));
        userDto.setUpdatedTimestamp(new Timestamp(user.getUpdated().getTime()));
        //userDto.removeNanos();
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        UserValidator.validateUser(userDto);
        User user = new User();
        user.setId(user.getId());
        user.setUserName(user.getUserName());
        return user;
    }

    public static List<UserDto> toDto(List<User> users) {
        List<UserDto> userDto = new ArrayList<>(users.size());
        users.forEach(user -> userDto.add(toDto(user)));
        return userDto;
    }
}
