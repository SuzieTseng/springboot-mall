package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.dto.UserRegisterRequest;
import com.susankim.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
