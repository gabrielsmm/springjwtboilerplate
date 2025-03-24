package com.gabrielsmm.springjwtboilerplate.controllers.exceptions;

import com.gabrielsmm.springjwtboilerplate.services.exceptions.ErroAoSalvarObjetoException;
import com.gabrielsmm.springjwtboilerplate.services.exceptions.IntegridadeDeDadosException;
import com.gabrielsmm.springjwtboilerplate.services.exceptions.ObjetoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> objetoNaoEncontrado(ObjetoNaoEncontradoException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IntegridadeDeDadosException.class)
    public ResponseEntity<ErroPadrao> integridadeDeDados(IntegridadeDeDadosException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ErroAoSalvarObjetoException.class)
    public ResponseEntity<ErroPadrao> erroAoSalvarObjeto(ErroAoSalvarObjetoException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro ao salvar objeto", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ErroValidacao erro = new ErroValidacao(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            erro.addErro(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

}
