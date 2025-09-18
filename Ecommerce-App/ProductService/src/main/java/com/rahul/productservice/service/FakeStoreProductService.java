package com.rahul.productservice.service;

import com.rahul.productservice.dto.FakeStoreResponseDTO;
import com.rahul.productservice.model.Category;
import com.rahul.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreResponse")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;// if we want to call any another server for that we use external library called restTemplate
    private RedisTemplate<String,Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate,RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    public Product getProductDetails(Integer id){

        // below code is for communication of one service to another - START
        restTemplate.getForObject("http://authservice/users/sample",void.class);
        // http://authservice/users/sample -> here authservice is service name that we can in Eureka
        // below code is for communication of one service to another - END



        // below code is for the redis start
        Product product1 = (Product) redisTemplate.opsForHash().get("PRODUCTS","PRODUCT_"+id);
        if(product1 !=null)
             return product1;      // this is Cache HIT
        // below code is for the redis end




        // Cache Miss
        Product product = new Product();
        // call fakestore API -->  call another service called RestTemplate
        ResponseEntity<FakeStoreResponseDTO> fakeStoreResponse = restTemplate.getForEntity("https://fakestoreapi.com/products/"+id, FakeStoreResponseDTO.class);

        // get the response
        FakeStoreResponseDTO response = fakeStoreResponse.getBody();
        if(response == null){
            throw new IllegalArgumentException("FakeStoreResponse is null");
        }

        // map the response to our product  model
        product = convertFakeStoreResponseToProduct(response);


        // below code is for the Redis, storing the data into redis
        redisTemplate.opsForHash().put("PRODUCTS","PRODUCT_"+id,product);
        // below code is for the Redis, storing the data into redis
        return product;
    }

    private Product convertFakeStoreResponseToProduct(FakeStoreResponseDTO response) {

        Product product = new Product();
        Category category = new Category();
        category.setTitle(response.getCategory());
        product.setCategory(category);

        product.setId(response.getId());
        product.setDescription(response.getDescription());
        product.setImageURL(response.getImage());
        product.setTitle(response.getTitle());

        return product;
    }





    public List<Product> getAllProductsDetails() {
        List<Product> response = new ArrayList<>();
        ResponseEntity<FakeStoreResponseDTO[]> fakeStoreResponse = restTemplate.getForEntity("https://fakestoreapi.com/products/", FakeStoreResponseDTO[].class);

        for(FakeStoreResponseDTO fakeStoreResponseDTO : fakeStoreResponse.getBody()){
            response.add(convertFakeStoreResponseToProduct(fakeStoreResponseDTO));
        }
        return response;
    }





    public Product createTheProduct(Product product) {
        Product response = new Product();

        FakeStoreResponseDTO requestBody = new FakeStoreResponseDTO();
        requestBody.setCategory(product.getCategory().getTitle());
        requestBody.setDescription(product.getDescription());
        requestBody.setTitle(product.getTitle());
        requestBody.setImage(product.getImageURL());

        ResponseEntity<FakeStoreResponseDTO> fakeStoreResponse  =
                restTemplate.postForEntity("https://fakestoreapi.com/products", requestBody, FakeStoreResponseDTO.class);

         response = convertFakeStoreResponseToProduct(fakeStoreResponse.getBody());
        return response;
    }

    @Override
    public Page<Product> getPaginatedproducts(int pageNumber, int pageSize) {
        return null;
    }
}






// call fakestore API -->  call another service called RestTemplate
// get the response
// map the response to our product  model
// return