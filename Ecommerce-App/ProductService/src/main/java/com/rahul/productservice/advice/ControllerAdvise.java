package com.rahul.productservice.advice;

import com.rahul.productservice.dto.ErrorDTO;
import com.rahul.productservice.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleException(){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode("400");
        errorDTO.setErrorMsg("Bad Request");
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode("404");
        errorDTO.setErrorMsg("Product Not Found");

        return ResponseEntity.badRequest().body(errorDTO);
    }
}
