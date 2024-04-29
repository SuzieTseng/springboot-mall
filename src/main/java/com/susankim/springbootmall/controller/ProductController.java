package com.susankim.springbootmall.controller;

import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductRequest productRequest){

        Integer productId = productService.addProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}
