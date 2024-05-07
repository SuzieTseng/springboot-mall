package com.susankim.springbootmall.controller;

import com.susankim.springbootmall.dto.CreateOrderRequest;
import com.susankim.springbootmall.dto.OrderQueryParams;
import com.susankim.springbootmall.dto.Page;
import com.susankim.springbootmall.model.Order;
import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "6") @Max(24) @Min(1) Integer numPerPage,
                                                 @RequestParam(defaultValue = "1") @Min(1) Integer page) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setNumPerPage(numPerPage);
        orderQueryParams.setPage(page);

        // Obtain order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // Obtain order total number
        Integer total = orderService.orderCount(orderQueryParams);

        // Pagination
        Page<Order> orderPage = new Page<>();
        orderPage.setNumPerPage(numPerPage);
        orderPage.setPage(page);
        orderPage.setTotal(total);
        orderPage.setResult(orderList);

        return new ResponseEntity<>(orderPage, HttpStatus.OK);

    }

    @PostMapping("users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }
}
