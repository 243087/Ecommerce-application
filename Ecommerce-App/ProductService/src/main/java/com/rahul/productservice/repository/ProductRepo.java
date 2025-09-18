package com.rahul.productservice.repository;

import com.rahul.productservice.model.Category;
import com.rahul.productservice.model.Product;
import com.rahul.productservice.repository.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;    
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> { // JpaRepository takes two parameter 1st Model name and 2nd data type of Primary key of Model name

    //  below we are using the JPA queries
    Optional<Product> findById(Integer id);
    Product save(Product p);
    Optional<Product> findByCategory(Category c);
    // Select * from products where id = id and category = c;
    Optional<Product> findByIdAndCategory(Integer id, Category c);
    Optional<List<Product>> findAllByCategory(Category c);
    void deleteById(Integer id);
    void deleteAllByCategory(Category c);


    // below we are using the HQL(Hibernate Query Language)
    @Query("select p.title, p.description from Product p where p.title =:title")
    ProductProjection getProductNameByTitle(@Param("title") String title);

    @Query("select p from Product p where p.title =:title and p.id=:id")
    Product getProductByTitleAndByProductId(@Param("title") String title, @Param("id") Integer id);
}
