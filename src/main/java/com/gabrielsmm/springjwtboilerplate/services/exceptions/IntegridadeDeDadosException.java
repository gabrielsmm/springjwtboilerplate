package com.gabrielsmm.springjwtboilerplate.services.exceptions;

public class IntegridadeDeDadosException extends RuntimeException {

    public IntegridadeDeDadosException(String message) {
        super(message);
    }

    public IntegridadeDeDadosException(String message, Throwable cause) {
        super(message, cause);
    }

}
