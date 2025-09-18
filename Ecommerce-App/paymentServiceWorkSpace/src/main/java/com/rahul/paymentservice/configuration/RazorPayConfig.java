package com.rahul.paymentservice.configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("rzp_test_QKMc862gNrpfFB")
    private String razorpayId;
    @Value("8cTg8SYDwoAMZzZCbNK65zG0")
    private String razorpaySecret;


    @Bean
    public RazorpayClient razorpayClient(){
        try {
            return new RazorpayClient(razorpayId, razorpaySecret);
        }
        catch (RazorpayException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
