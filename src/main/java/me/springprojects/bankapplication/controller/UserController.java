package me.springprojects.bankapplication.controller;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        UserDTO res = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping(path = "/all")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping(path = "/deposit")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void depositMoney(@RequestParam(name = "amount") BigDecimal amount){
        userService.depositMoney(amount);
    }

    @PutMapping(path = "/withdraw")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void withdrawMoney(@RequestParam(name = "amount") BigDecimal amount){
        userService.withdrawMoney(amount);
    }

    @PutMapping(path = "/transfer")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void transferMoney(@RequestParam(name = "amount") BigDecimal amount, @RequestParam(name = "id") String receiverId){
        userService.transferMoney(amount, receiverId);
    }
}
