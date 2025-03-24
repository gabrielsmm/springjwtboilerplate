package com.gabrielsmm.springjwtboilerplate.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampoMensagem {

    private String nomeCampo;
    private String mensagem;

}
