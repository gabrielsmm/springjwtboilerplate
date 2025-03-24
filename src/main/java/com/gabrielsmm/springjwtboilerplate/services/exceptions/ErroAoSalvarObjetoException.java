package com.gabrielsmm.springjwtboilerplate.services.exceptions;

public class ErroAoSalvarObjetoException extends RuntimeException {

    public ErroAoSalvarObjetoException(String message) {
        super(message);
    }

    public ErroAoSalvarObjetoException(String message, Throwable cause) {
        super(message, cause);
    }

}
