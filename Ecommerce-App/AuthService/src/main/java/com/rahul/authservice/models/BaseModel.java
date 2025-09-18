package com.rahul.authservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass     // this annotation used bcoz we don't want this class BaseModel to create a table in database
@EntityListeners(AuditingEntityListener.class) // this annotation tells that in Db fields like createdAt, lastModifiedAt will get automatically added
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date lastModifiedAt;

    private boolean deleted;


}
