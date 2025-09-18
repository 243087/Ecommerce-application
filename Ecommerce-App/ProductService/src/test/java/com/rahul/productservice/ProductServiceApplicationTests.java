package com.rahul.productservice;

import com.rahul.productservice.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ProductRepo productRepo;

    @Test
    void contextLoads() {
    }

}
