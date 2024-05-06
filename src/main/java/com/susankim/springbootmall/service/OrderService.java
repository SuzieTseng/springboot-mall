package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dto.CreateOrderRequest;
import com.susankim.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
