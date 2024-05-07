package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dao.OrderDao;
import com.susankim.springbootmall.dao.ProductDao;
import com.susankim.springbootmall.dao.UserDao;
import com.susankim.springbootmall.dto.BuyItem;
import com.susankim.springbootmall.dto.CreateOrderRequest;
import com.susankim.springbootmall.dto.OrderQueryParams;
import com.susankim.springbootmall.model.Order;
import com.susankim.springbootmall.model.OrderItem;
import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 檢查使用者是否存在
        User user = userDao.getUserById(userId);

        if (user == null) {

            log.warn("user {} does not exist", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalPrice = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查商品庫存
            if (product == null) {
                log.warn("product {} does not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("product {} has insufficient stock ({}) for your order ({})",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總價
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

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer orderCount(OrderQueryParams orderQueryParams) {
        return orderDao.orderCount(orderQueryParams);
    }
}
