package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.constant.ProductCategory;
import com.susankim.springbootmall.dto.OrderQueryParams;
import com.susankim.springbootmall.dto.ProductQueryParams;
import com.susankim.springbootmall.model.Order;
import com.susankim.springbootmall.model.OrderItem;
import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.rowmapper.OrderItemRowMapper;
import com.susankim.springbootmall.rowmapper.OrderRowMapper;
import com.susankim.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, int totalPrice) {
        String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                "VALUES (:userId, :totalPrice, :createTime, :updateTime)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalPrice", totalPrice);

        Date now = new Date();
        map.put("createTime", now);
        map.put("updateTime", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :price)";

        MapSqlParameterSource [] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("price", orderItem.getPrice());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);

    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList != null && orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {

        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "FROM order_item AS oi " +
                "LEFT JOIN product AS p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return  orderItemList;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // Filtering
        sql = addFilteringSql(sql, map, orderQueryParams);

        // Sorting

        sql += " ORDER BY created_date DESC";

        // Pagination
        sql += " LIMIT :limit OFFSET :offset";

        Integer page = orderQueryParams.getPage();
        Integer numPerPage = orderQueryParams.getNumPerPage();

        map.put("limit", numPerPage);
        map.put("offset", (page-1)*numPerPage);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        return orderList;
    }

    @Override
    public Integer orderCount(OrderQueryParams orderQueryParams) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // Filtering
        sql = addFilteringSql(sql, map, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        Integer userId = orderQueryParams.getUserId();

        if (userId != null) {
            sql += " AND user_id = :userId";
            map.put("userId", userId);
        }

        return sql;
    }
}
