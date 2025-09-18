package com.rahul.productservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    @RequestMapping("/check")
    public String chck(){
        return "Working fine!!!";
    }
}
