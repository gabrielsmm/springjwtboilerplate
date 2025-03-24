package com.gabrielsmm.springjwtboilerplate.controllers;

import com.gabrielsmm.springjwtboilerplate.dtos.UsuarioRequestDTO;
import com.gabrielsmm.springjwtboilerplate.dtos.UsuarioResponseDTO;
import com.gabrielsmm.springjwtboilerplate.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping(value = "/nome/{nomeUsuario}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorNome(@PathVariable String nomeUsuario) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorNomeUsuario(nomeUsuario);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuariosPaginados(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") int tamanho) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarUsuariosPaginados(pagina, tamanho);
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO usuario = usuarioService.salvar(usuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO usuario = usuarioService.atualizar(id, usuarioDTO);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
