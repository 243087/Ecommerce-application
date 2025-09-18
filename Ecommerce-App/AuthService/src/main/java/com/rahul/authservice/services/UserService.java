package com.rahul.authservice.services;

import com.rahul.authservice.models.Token;
import com.rahul.authservice.models.User;

public interface UserService {
   Token login(String email, String password);

   User signUp(String name, String email, String password);

   User validateToken(String tokenValue);

   void logout(String tokenValue);

}
