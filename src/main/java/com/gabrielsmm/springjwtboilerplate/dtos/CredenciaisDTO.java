package com.gabrielsmm.springjwtboilerplate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredenciaisDTO {

    private String nomeUsuario;
    private String senha;

}
