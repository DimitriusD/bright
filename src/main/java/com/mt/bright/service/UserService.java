package com.mt.bright.service;

import com.mt.bright.dao.RoleRepository;
import com.mt.bright.dao.UserRepository;
import com.mt.bright.dto.UserDTO;
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

    public Users registerNewUser(UserDTO userDTO){
        if(Objects.nonNull(userDTO) && Objects.nonNull(userDTO.getPhone())){

            Users existedUsers = userRepository.findByPhone(userDTO.getPhone());

            if(Objects.isNull(existedUsers)){
                Users users = modelMapper.map(userDTO, Users.class);
                users.setPass(passwordEncoder.encode(userDTO.getPass()));
                users.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                return userRepository.save(users);
            }

            return existedUsers;
        }
        return null;
    }

    public Users login(UserDTO user){
        if(Objects.nonNull(user) && Objects.nonNull(user.getPhone())){
            return userRepository.findByPhoneAndPass(user.getPhone(), user.getPass());
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);
        if(Objects.isNull(users)){
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(users.getUsername(), users.getPass(), authorities);
    }

    public Users getById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public Users getByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    public void update(UserDTO updateProfile){
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
