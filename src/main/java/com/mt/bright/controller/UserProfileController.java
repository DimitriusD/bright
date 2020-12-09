package com.mt.bright.controller;

import com.mt.bright.dao.UserProfileRepository;
import com.mt.bright.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping("/user_profile")
public class UserProfileController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping()
    public UserProfile create(@RequestBody UserProfile userProfile){
        if(Objects.nonNull(userProfile)){
            return userProfileRepository.save(userProfile);
        }
        return null;
    }

    @GetMapping("/{id}")
    public UserProfile readById(@PathVariable Long id){
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        return userProfile.orElse(null);

    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserProfile updateProfile, @PathVariable Long id){
        userProfileRepository.findById(id)
                .map(profile -> {
                    profile.setAge(updateProfile.getAge());
                    profile.setSurname(updateProfile.getSurname());
                    profile.setName(updateProfile.getName());
                    profile.setInterest(updateProfile.getInterest());
                    return userProfileRepository.save(updateProfile);
                })
                .orElseGet(() ->
                        {
                            updateProfile.setId(id);
                            return userProfileRepository.save(updateProfile);
                        }
                );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userProfileRepository.deleteById(id);
    }
}
