package com.gabrielsmm.springjwtboilerplate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielsmm.springjwtboilerplate.dtos.CredenciaisDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CredenciaisDTO credenciais = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciais.getNomeUsuario(), credenciais.getSenha(), new ArrayList<>());
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        String nomeUsuario = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
        String token = jwtUtil.gerarToken(nomeUsuario);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", "Bearer " + token);
    }

}
