package com.rahul.productservice.controller;

import com.rahul.productservice.dto.CreateProductRequestDTO;
import com.rahul.productservice.exception.ProductNotFoundException;
import com.rahul.productservice.model.Category;
import com.rahul.productservice.model.Product;
import com.rahul.productservice.service.FakeStoreProductService;
import com.rahul.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService service;
    public ProductController(@Qualifier("fakeStoreResponse") ProductService service) {
        this.service = service;
    }

    @GetMapping("/products/{id}")  // this annotation tells that this is web application function
    public Product getProductById(@PathVariable("id") Integer id) throws ProductNotFoundException {
        if(id==10000){
            throw new IllegalArgumentException("Id cannot be 10000");
        }
        Product product =  service.getProductDetails(id);
        if(product==null){
            throw new ProductNotFoundException("Product not found");
        }

        return product;
    }


    @GetMapping("/products")
    public List<Product> getAllProducts(){

        List<Product> result = service.getAllProductsDetails();
        return result;
    }











    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDTO request){

    // input validation for null check
    if(request.getTitle()==null){
        throw new IllegalArgumentException("Title cannot be null");
    }
    if(request.getDescription()==null){
        throw new IllegalArgumentException("Description cannot be null");
    }
    if(request.getCategory()==null){
        throw new IllegalArgumentException("Category cannot be null");
    }
    if(request.getImageURL()==null){
        throw new IllegalArgumentException("ImageURL cannot be null");
    }

     return service.createTheProduct(convertDTOToProduct(request));

    }

    private Product convertDTOToProduct(CreateProductRequestDTO request) {
        Product product = new Product();
        Category category = new Category();
        category.setTitle(request.getTitle());
        product.setCategory(category);
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setImageURL(request.getImageURL());
        return product;
    }


    // for pagination supported api
    @GetMapping("/products/{pageNo}/{pageSize}")
    public ResponseEntity<Page<Product>> getPaginatedproducts(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        Page<Product> products = service.getPaginatedproducts(pageNo,pageSize);
        return ResponseEntity.ok(products);

    }



    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable("id") Integer id){

    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable("id") Integer id){

    }

}
