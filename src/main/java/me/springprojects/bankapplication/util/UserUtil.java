package me.springprojects.bankapplication.util;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getUserFromSecurityContext(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.getUserByEmail(userEmail);

        return user.get(); // should never be null
    }
}
