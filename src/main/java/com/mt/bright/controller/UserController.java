package com.mt.bright.controller;

import com.mt.bright.dto.UserDTO;
import com.mt.bright.entity.Users;
import com.mt.bright.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("/registration")
    public Users registration (@RequestBody UserDTO user){
        return userService.registerNewUser(user);
    }

    @PostMapping("/login")
    public Users login (@RequestBody UserDTO user){
        return userService.login(user);
    }

    @GetMapping("/{id}")
    public Users getById(@PathVariable Long id){
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

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
