package com.gabrielsmm.springjwtboilerplate.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Classe de transferência de dados para requisições de usuários.
 */
public class UsuarioRequestDTO {

    @NotBlank(message = "Preencha o campo corretamente")
    @Size(min = 3, max = 50, message = "Nome de usuário precisa estar entre {min} e {max} caracteres")
    private String nomeUsuario;

    @NotBlank(message = "Preencha o campo corretamente")
    @Size(min = 5, max = 50, message = "Senha precisa estar entre {min} e {max} caracteres")
    private String senha;

    private Set<Integer> perfis;

}
