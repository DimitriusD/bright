package com.mt.bright.service;

import com.mt.bright.dao.RoleRepository;
import com.mt.bright.dao.UserRepository;
import com.mt.bright.dto.UserDTO;
import com.mt.bright.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service
public class UserService {

    private ModelMapper modelMapper;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public User registerNewUser(UserDTO userDTO){
        if(Objects.nonNull(userDTO) && Objects.nonNull(userDTO.getPhone())){

            User existedUser = userRepository.findByPhone(userDTO.getPhone());

            if(Objects.isNull(existedUser)){
                User user = modelMapper.map(userDTO, User.class);
                user.setPass(passwordEncoder.encode(userDTO.getPass()));
                user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                return userRepository.save(user);
            }

            return existedUser;
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
        return userRepository.findById(id).orElse(null);
    }

    public User getByPhone(String phone){
        return userRepository.findByPhone(phone);
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

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
