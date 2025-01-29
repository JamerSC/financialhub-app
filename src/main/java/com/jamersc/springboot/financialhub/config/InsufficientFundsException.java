package com.jamersc.springboot.financialhub.config;

import java.io.Serial;

public class InsufficientFundsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

<<<<<<< HEAD
=======
    // Custom Class for Runtime Exception
>>>>>>> 8723a7db6bbf6f331c4b2199efc44cd7c212925d
    public InsufficientFundsException(String message) {
        super(message);
    }
}
