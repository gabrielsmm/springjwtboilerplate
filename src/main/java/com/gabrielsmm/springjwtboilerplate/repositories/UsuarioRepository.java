package com.gabrielsmm.springjwtboilerplate.repositories;

import com.gabrielsmm.springjwtboilerplate.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

}
