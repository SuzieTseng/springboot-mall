package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.dto.OrderQueryParams;
import com.susankim.springbootmall.model.Order;
import com.susankim.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalPrice);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer orderCount(OrderQueryParams orderQueryParams);
}
