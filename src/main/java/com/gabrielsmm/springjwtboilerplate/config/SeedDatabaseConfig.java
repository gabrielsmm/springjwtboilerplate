package com.gabrielsmm.springjwtboilerplate.config;

import com.gabrielsmm.springjwtboilerplate.entities.Usuario;
import com.gabrielsmm.springjwtboilerplate.entities.enums.Perfil;
import com.gabrielsmm.springjwtboilerplate.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class SeedDatabaseConfig {

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Método para popular o banco de dados com um usuário admin caso não exista nenhum usuário cadastrado.
     *
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNomeUsuario("admin");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.addPerfil(Perfil.ADMIN);
                usuarioRepository.save(admin);
            }
        };
    }

}
