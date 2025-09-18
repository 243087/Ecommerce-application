package com.rahul.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Product extends BaseModel{
    private Integer id;
    private String title;
    private String description;
    private String imageURL;

@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
@JsonIgnore
// if u have obj of class A into B and class B into A then to avoid infinite loop we use this
    private Category category;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Category getCategory() {
        return category;
    }
}
