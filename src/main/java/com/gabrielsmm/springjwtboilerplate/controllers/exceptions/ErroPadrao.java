package com.gabrielsmm.springjwtboilerplate.controllers.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ErroPadrao {

    private String dataHora;
    private Integer status;
    private String erro;
    private String mensagem;
    private String caminho;

    public ErroPadrao(LocalDateTime dataHora, Integer status, String erro, String mensagem, String caminho) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dataHora = dataHora.format(formatter);
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }

}
