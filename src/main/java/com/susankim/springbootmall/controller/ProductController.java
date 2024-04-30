package com.susankim.springbootmall.controller;

import com.susankim.springbootmall.constant.ProductCategory;
import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(ProductCategory category, String search) {
        List<Product> productList = productService.getProducts(category, search);

//        return ResponseEntity.status(HttpStatus.OK).body(productList);
        return ResponseEntity.ok(productList);
    }

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

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(productId);
        if (product == null){
            return ResponseEntity.notFound().build();
        }

        productService.updateProduct(productId, productRequest);

        Product updated = productService.getProductById(productId);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

}
