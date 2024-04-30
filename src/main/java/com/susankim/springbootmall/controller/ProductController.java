package com.susankim.springbootmall.controller;

import com.susankim.springbootmall.constant.ProductCategory;
import com.susankim.springbootmall.dto.Page;
import com.susankim.springbootmall.dto.ProductQueryParams;
import com.susankim.springbootmall.dto.ProductRequest;
import com.susankim.springbootmall.model.Product;
import com.susankim.springbootmall.service.ProductService;
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
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // Filtering
            ProductCategory category,
            String search,
            // Sorting
            @RequestParam(defaultValue = "created_date") String order,
            @RequestParam(defaultValue = "desc") String sort,
            // Pagination
            @RequestParam(defaultValue = "4") @Max(48) @Min(1) Integer numPerPage,
            @RequestParam(defaultValue = "1") @Min(1) Integer page
             ) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrder(order);
        productQueryParams.setSort(sort);
        productQueryParams.setNumPerPage(numPerPage);
        productQueryParams.setPage(page);

        // Obtain product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // Obtain product total number
        Integer total = productService.productCount(productQueryParams);

        // Pagination
        Page<Product> productPage = new Page<>();
        productPage.setNumPerPage(numPerPage);
        productPage.setPage(page);
        productPage.setTotal(total);
        productPage.setResult(productList);

//        return ResponseEntity.status(HttpStatus.OK).body(productList);
        return ResponseEntity.ok(productPage);
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
