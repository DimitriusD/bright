package com.mt.bright.dao;

import com.mt.bright.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    Users findByUsernameAndPassword(String username, String password);

    Users findByPhone(String phone);

    Users findByUsername(String username);
}
