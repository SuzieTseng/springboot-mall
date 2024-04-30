package com.susankim.springbootmall.dao;

import com.susankim.springbootmall.constant.ProductCategory;
import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer addProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
