package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dao.OrderDao;
import com.susankim.springbootmall.dao.ProductDao;
import com.susankim.springbootmall.dto.BuyItem;
import com.susankim.springbootmall.dto.CreateOrderRequest;
import com.susankim.springbootmall.model.Order;
import com.susankim.springbootmall.model.OrderItem;
import com.susankim.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalPrice = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            // 計算總價
            Product product = productDao.getProductById(buyItem.getProductId());
            int price = product.getPrice() * buyItem.getQuantity();
            totalPrice += price;

            // 將 API 輸入的 buyItem 轉換為 預備存入資料庫的 orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setPrice(price);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalPrice);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemListList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemListList);

        return order;
    }
}
