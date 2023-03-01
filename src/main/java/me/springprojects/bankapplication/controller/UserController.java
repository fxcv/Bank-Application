package me.springprojects.bankapplication.controller;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping(path = "/all")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
}
