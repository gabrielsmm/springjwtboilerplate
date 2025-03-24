package com.gabrielsmm.springjwtboilerplate.services;

import com.gabrielsmm.springjwtboilerplate.entities.Usuario;
import com.gabrielsmm.springjwtboilerplate.repositories.UsuarioRepository;
import com.gabrielsmm.springjwtboilerplate.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario).orElse(null);
        if (usuario == null) {
            throw new UsernameNotFoundException(nomeUsuario);
        }
        return new UserDetailsImpl(usuario.getId(), usuario.getNomeUsuario(), usuario.getSenha(), usuario.getPerfis());
    }

}
