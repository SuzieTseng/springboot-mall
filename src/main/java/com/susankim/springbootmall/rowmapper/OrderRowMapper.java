package com.susankim.springbootmall.rowmapper;

import com.susankim.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();

        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalPrice(rs.getInt("total_amount"));
        order.setCreateTime(rs.getTimestamp("created_date"));
        order.setUpdateTime(rs.getTimestamp("last_modified_date"));

        return order;
    }
}
