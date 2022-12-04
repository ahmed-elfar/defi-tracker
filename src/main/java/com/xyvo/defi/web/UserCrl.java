package com.xyvo.defi.web;

import com.xyvo.defi.dto.UserDto;
import com.xyvo.defi.service.UserService;
import com.xyvo.defi.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class UserCrl {

    private final UserService userService;

    @Autowired
    public UserCrl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> users() {
        return userService.findAll();
    }

    @GetMapping("/user")
    public UserDto getUser(@RequestParam(name = "id") long id) {
        UserValidator.validateId(id);
        return userService.getById(id);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestParam(name = "userName") String userName) {
        UserValidator.validateName(userName);
        return userService.createUser(userName);
    }

    @PutMapping("/user")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        UserValidator.validateUser(userDto);
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam(name = "id") long id) {
        UserValidator.validateId(id);
        userService.deleteById(id);
    }

}
