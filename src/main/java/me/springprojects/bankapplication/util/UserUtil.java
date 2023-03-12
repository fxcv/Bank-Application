package me.springprojects.bankapplication.util;

import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.security.UserDetailsImpl;
import me.springprojects.bankapplication.service.enums.UserExceptions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public User getUserFromSecurityContext(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetailsImpl userDetails){
            return userDetails.getUser();
        }
        throw new InvalidInputDataException(UserExceptions.AUTH_REQUIRED);
    }
}
