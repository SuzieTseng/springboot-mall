package com.susankim.springbootmall.rowmapper;

import com.susankim.springbootmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreateTime(rs.getTimestamp("created_date"));
        user.setUpdateTime(rs.getTimestamp("last_modified_date"));

        return user;
    }
}