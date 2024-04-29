package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
