package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dto.UserRegisterRequest;
import com.susankim.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
