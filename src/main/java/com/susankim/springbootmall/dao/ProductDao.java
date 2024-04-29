package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer addProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
