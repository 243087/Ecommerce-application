package com.rahul.authservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Entity(name = "user")
public class User extends BaseModel{
  private String name;
  private String password;
  private String email;

  @ManyToMany
  private List<Role> roles;
}
