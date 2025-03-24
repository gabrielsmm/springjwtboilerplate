package com.gabrielsmm.springjwtboilerplate.controllers.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErroValidacao extends ErroPadrao {

    private List<CampoMensagem> erros = new ArrayList<>();

    public ErroValidacao(LocalDateTime dataHora, Integer status, String erro, String mensagem, String caminho) {
        super(dataHora, status, erro, mensagem, caminho);
    }

    public void addErro(String nomeCampo, String mensagem) {
        erros.add(new CampoMensagem(nomeCampo, mensagem));
    }

}
