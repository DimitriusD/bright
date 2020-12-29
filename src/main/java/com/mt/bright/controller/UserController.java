package com.mt.bright.controller;

import com.mt.bright.dto.UserDTO;
import com.mt.bright.entity.User;
import com.mt.bright.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public User registration (@RequestBody UserDTO user){
        return userService.registerNewUser(user);
    }

    @PostMapping("/login")
    public User login (@RequestBody UserDTO user){
        return userService.login(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return userService.getById(id);

    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserDTO updateProfile){
        userService.update(updateProfile);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
