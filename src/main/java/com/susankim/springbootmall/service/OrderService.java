package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dto.CreateOrderRequest;
import com.susankim.springbootmall.dto.OrderQueryParams;
import com.susankim.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer orderCount(OrderQueryParams orderQueryParams);
}
