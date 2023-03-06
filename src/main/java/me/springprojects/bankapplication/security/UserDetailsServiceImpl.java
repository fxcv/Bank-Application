package me.springprojects.bankapplication.security;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmail(username); // login by email
        return user.map(u -> new UserDetailsImpl(u, passwordEncoder))
                   .orElseThrow();
    }
}
