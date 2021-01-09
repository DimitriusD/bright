package com.mt.bright.service;

import com.mt.bright.dao.RoleRepository;
import com.mt.bright.dao.UserRepository;
import com.mt.bright.dto.UserRequestDTO;
import com.mt.bright.dto.UserResponseDTO;
import com.mt.bright.entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private ModelMapper modelMapper;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserResponseDTO registerNewUser(UserRequestDTO userRequestDTO){
        Users user;

        if(Objects.isNull(userRequestDTO) || Objects.isNull(userRequestDTO.getPhone())){
            throw new IllegalArgumentException();
        }

        user = userRepository.findByPhone(userRequestDTO.getPhone());

        if(Objects.isNull(user)){
                user = modelMapper.map(userRequestDTO, Users.class);
                user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
                user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                userRepository.save(user);
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .age(user.getAge())
                .build();
    }

    public UserResponseDTO login(UserRequestDTO userRequestDTO){

        if(Objects.isNull(userRequestDTO) && Objects.isNull(userRequestDTO.getPhone())){
            throw new IllegalArgumentException();
        }

        Users user = userRepository.findByPhone(userRequestDTO.getPhone());

        if(Objects.isNull(user) && passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("Credential is invalid");
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .age(user.getAge())
                .password(userRequestDTO.getPassword())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Users users = userRepository.findByPhone(phone);
        if(Objects.isNull(users)){
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(users.getPhone(), users.getPassword(), authorities);
    }

    public Users getById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public Users getByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    public void update(UserRequestDTO updateProfile){
        userRepository.findById(updateProfile.getId())
                .map(profile -> {
                    profile.setAge(updateProfile.getAge());
                    profile.setSurname(updateProfile.getSurname());
                    profile.setUsername(updateProfile.getUsername());
                    return userRepository.save(profile);
                })
                .orElseGet(() ->
                        userRepository.save(modelMapper.map(updateProfile, Users.class))
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
