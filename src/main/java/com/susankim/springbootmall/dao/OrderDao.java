package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalPrice);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
