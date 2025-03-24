package com.gabrielsmm.springjwtboilerplate.security;

import com.gabrielsmm.springjwtboilerplate.entities.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String nomeUsuario;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String nomeUsuario, String senha, Set<Perfil> perfis) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeUsuario;
    }

    public boolean hasRole(Perfil perfil) {
        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
    }

}
