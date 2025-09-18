package com.rahul.authservice.controllers;

import com.rahul.authservice.dtos.*;
import com.rahul.authservice.models.Token;
import com.rahul.authservice.models.User;
import com.rahul.authservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  //  it represent that this controller is going to hold the Rest api / http api's
@RequestMapping("/users") //  all api's of this controller will starts from the  /users  like  localhost:8080/users/
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto){
        Token token = userService.login(requestDto.getEmail(), requestDto.getPassword());

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setTokenValue(token.getValue());
        return responseDto;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto){

       User user = userService.signUp(requestDto.getName(),requestDto.getEmail(),requestDto.getPassword());
       return UserDto.from(user);

    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogOutRequestDto requestDto){
        userService.logout(requestDto.getTokenValue());
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateToken(@PathVariable("token") String tokenValue){
//  How to know token is valid or not
//  Token value should be present in the DB, isDeleted should be false and expiry time should be greater current time
        User user = userService.validateToken(tokenValue);
        ResponseEntity<UserDto> responseEntity = null;
        if(user == null){
            responseEntity = new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else{
            responseEntity = new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping("/sample")
    public void sampleApiforCrossService(){
        System.out.println("Got the sampleApiforCrossService ");
    }
}
