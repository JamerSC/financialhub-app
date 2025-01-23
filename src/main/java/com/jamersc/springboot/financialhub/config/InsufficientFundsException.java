package com.jamersc.springboot.financialhub.config;

import java.io.Serial;

public class InsufficientFundsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    // Custom Class for Runtime Exception
    public InsufficientFundsException(String message) {
        super(message);
    }
}
