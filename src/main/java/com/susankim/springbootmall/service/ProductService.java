package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer addProduct(ProductRequest productRequest);
}
