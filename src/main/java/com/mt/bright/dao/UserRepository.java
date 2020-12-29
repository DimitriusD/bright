package com.mt.bright.dao;

import com.mt.bright.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByPhoneAndPass(String phone, String pass);

    User findByPhone(String phone);
}
