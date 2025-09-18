package com.rahul.authservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.authservice.dtos.SendEmailDto;
import com.rahul.authservice.models.Token;
import com.rahul.authservice.models.User;
import com.rahul.authservice.repositories.TokenRepository;
import com.rahul.authservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private KafkaTemplate<String,String> kafkaTemplate;  // key -> Topic , Value = Event
    private ObjectMapper objectMapper;    // this is used for Serialization(Converts a Java object into a JSON string)
                                          // and - Deserialization(Converts a JSON string back into a Java object)



    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRepository userRepository,
                           TokenRepository tokenRepository,
                           KafkaTemplate kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            // throw exception or redirect to login page since this user is not registered in database
            return null;
        }

        // Match the password
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            // throw exception since password didn't match
            return null;
        }

        // login successfully and generate the token
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(120));
        token.setUser(user);

        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearLater = currentDate.plusYears(1);
        Date dateFormat = Date.from(oneYearLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        token.setExpiryAt(dateFormat);
        return tokenRepository.save(token);
    }

    @Override
    public User signUp(String name, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        //  this code is for emailService
        SendEmailDto emailDto = new SendEmailDto();
        emailDto.setSubject("Welcome to Scaler");
        emailDto.setEmail(email);
        emailDto.setBody("Thanks for signing up with Scaler");

        // Push an event to kafka
        try {
            kafkaTemplate.send("sendEmail",objectMapper.writeValueAsString(emailDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userRepository.save(user);
    }

    @Override
    public User validateToken(String tokenValue) {
        // to validate the token, token value should be present in the db and deleted should be false and expiry time > current time

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,false, new Date());

        if(optionalToken.isEmpty()){
            return null;
        }
        return optionalToken.get().getUser();
    }

    @Override
    public void logout(String tokenValue) {
        Token token = tokenRepository.findByValue(tokenValue);
        if (token != null) {
            tokenRepository.delete(token);
        }
    }
}
