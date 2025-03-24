package com.gabrielsmm.springjwtboilerplate.entities;

import com.gabrielsmm.springjwtboilerplate.entities.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUsuario;

    @JsonIgnore
    private String senha;

    /**
     * Define um conjunto de valores associados à entidade principal.
     * - Os valores são armazenados em uma tabela separada chamada "PERFIS".
     * - O carregamento é imediato (EAGER), garantindo que os dados estejam disponíveis ao buscar a entidade principal.
     * - Utiliza um Set para garantir que os valores sejam únicos.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    public Usuario() {
        addPerfil(Perfil.USUARIO);
    }

    public Usuario(Long id, String nomeUsuario, String senha) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        addPerfil(Perfil.USUARIO);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCodigo());
    }

}
