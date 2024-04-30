package com.susankim.springbootmall.service;

import com.susankim.springbootmall.dao.ProductDao;
import com.susankim.springbootmall.dto.ProductQueryParams;
import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        List<Product> productList = productDao.getProducts(productQueryParams);

        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer addProduct(ProductRequest productRequest) {
        return productDao.addProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public Integer productCount(ProductQueryParams productQueryParams) {
        return productDao.productCount(productQueryParams);
    }
}
