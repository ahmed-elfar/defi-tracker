package com.xyvo.defi.service;

import com.xyvo.defi.domain.profile.Settings;
import com.xyvo.defi.domain.profile.User;
import com.xyvo.defi.dto.UserDto;
import com.xyvo.defi.repository.api.UserRepo;
import com.xyvo.defi.server.ServerException;
import com.xyvo.defi.server.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xyvo.defi.mapper.UserMapper.toDto;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = userRepo.findAll();
        if(users.isEmpty()) {
            throw new ValidationException("Not Found.");
        }
        return toDto(users);
    }

    @Transactional(readOnly = true)
    public UserDto getById(long id) {
        User user = getOrElseThrow(id);
        return toDto(user);
    }

    @Transactional
    public UserDto createUser(String userName) {
        User user = userRepo.findByName(userName);
        if(user != null) {
            throw new ValidationException("Username already exists.");
        }
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setSettings(Settings.getDefaultSettings());
        newUser = userRepo.save(newUser);
        return toDto(newUser);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User userUpdate = getOrElseThrow(userDto.getId());
        if(userUpdate.getUserName().equals(userDto.getUserName())) {
            return toDto(userUpdate);
        }
        userUpdate.setUserName(userDto.getUserName());
        return toDto(userRepo.saveAndFlush(userUpdate));
//        userRepo.update(userDto.getId(), userDto.getUserName());
    }

    @Transactional
    public void deleteById(long id) {
        getOrElseThrow(id);
        userRepo.deleteById(id);
    }

    private User getOrElseThrow(long id) {
        return userRepo.findById(id).orElseThrow(() -> new ServerException("No such element."));
    }
}
