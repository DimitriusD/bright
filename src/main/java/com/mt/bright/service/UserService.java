package com.mt.bright.service;

import com.mt.bright.dao.UserRepository;
import com.mt.bright.dto.UserDTO;
import com.mt.bright.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public User registerNewUser(UserDTO user){
        if(Objects.nonNull(user) && Objects.nonNull(user.getPhone())){
            User existedUser = userRepository.findByPhone(user.getPhone());
            return Objects.isNull(existedUser)
                    ? userRepository.save(modelMapper.map(user, User.class))
                    : existedUser;
        }
        return null;
    }

    public User login(UserDTO user){
        if(Objects.nonNull(user) && Objects.nonNull(user.getPhone())){
            return userRepository.findByPhoneAndPass(user.getPhone(), user.getPass());
        }
        return null;
    }

    public User getById(Long id){
        Optional<User> userProfile = userRepository.findById(id);
        return userProfile.orElse(null);

    }

    public User getByPhone(String phone){
        User user = userRepository.findByPhone(phone);
        return user;

    }

    public void update(UserDTO updateProfile){
        userRepository.findById(updateProfile.getId())
                .map(profile -> {
                    profile.setAge(updateProfile.getAge());
                    profile.setSurname(updateProfile.getSurname());
                    profile.setName(updateProfile.getName());
                    return userRepository.save(profile);
                })
                .orElseGet(() ->
                        userRepository.save(modelMapper.map(updateProfile, User.class))
                );
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }


}
