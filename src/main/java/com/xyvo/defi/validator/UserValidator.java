package com.xyvo.defi.validator;

import com.xyvo.defi.dto.UserDto;
import com.xyvo.defi.server.ValidationException;
import com.xyvo.defi.utils.DomainUtils;



public class UserValidator {

    private UserValidator() {
    }

    public static boolean validateUser(UserDto user) {
        if(user == null) {
            throw new ValidationException("NULL user.");
        }
        validateId(user.getId());
        validateName(user.getUserName());
        return true;
    }

    public static void validateId(Long id) {
        if(id == null || id <= 0) {
            throw new ValidationException("Invalid Id param.");
        }
    }

    public static void validateName(String userName) {
        //use regex for username validation
        if(userName == null || userName.length() < 3 || userName.length() > DomainUtils.USER_NAME_LENGTH){
            throw new ValidationException("Username must be between 3 and 50 characters inclusive.");
        }
    }
}
