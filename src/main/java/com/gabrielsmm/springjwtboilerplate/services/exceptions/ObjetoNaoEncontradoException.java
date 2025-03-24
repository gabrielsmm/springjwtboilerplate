package com.gabrielsmm.springjwtboilerplate.services.exceptions;

public class ObjetoNaoEncontradoException extends RuntimeException {

    public ObjetoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ObjetoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
