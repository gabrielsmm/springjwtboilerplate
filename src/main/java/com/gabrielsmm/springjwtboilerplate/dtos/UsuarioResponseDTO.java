package com.gabrielsmm.springjwtboilerplate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Classe de transferência de dados para respostas de usuários.
 */
public class UsuarioResponseDTO {

    private Long id;
    private String nomeUsuario;
    private Set<Integer> perfis;

}
