package com.jamersc.springboot.financialhub.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalExceptionHandler {
<<<<<<< HEAD
=======
    // Class Method for trimming white space, blank, or empty space.
>>>>>>> 8723a7db6bbf6f331c4b2199efc44cd7c212925d
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
<<<<<<< HEAD

=======
    // Custom Class Method Exception Handler for Insufficient funds.
>>>>>>> 8723a7db6bbf6f331c4b2199efc44cd7c212925d
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
