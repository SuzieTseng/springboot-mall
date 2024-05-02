package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dao.UserDao;
import com.susankim.springbootmall.dto.UserRegisterRequest;
import com.susankim.springbootmall.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // verify if the account exists
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("{} has been registered", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return userDao.createUser(userRegisterRequest);
        }

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
