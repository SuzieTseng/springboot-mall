package com.susankim.springbootmall.controller;

import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if (product == null){
            return ResponseEntity.notFound().build();
        } else {
//            return ResponseEntity.status(HttpStatus.OK).body(product);
            return ResponseEntity.ok(product);
        }
    }

}
