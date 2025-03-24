package com.gabrielsmm.springjwtboilerplate.services;

import com.gabrielsmm.springjwtboilerplate.dtos.UsuarioRequestDTO;
import com.gabrielsmm.springjwtboilerplate.dtos.UsuarioResponseDTO;
import com.gabrielsmm.springjwtboilerplate.entities.Usuario;
import com.gabrielsmm.springjwtboilerplate.repositories.UsuarioRepository;
import com.gabrielsmm.springjwtboilerplate.services.exceptions.ErroAoSalvarObjetoException;
import com.gabrielsmm.springjwtboilerplate.services.exceptions.IntegridadeDeDadosException;
import com.gabrielsmm.springjwtboilerplate.services.exceptions.ObjetoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO buscarPorNomeUsuario(String nomeUsuario) {
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario).orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado! Nome de usuário: %s, Tipo: %s".formatted(nomeUsuario, Usuario.class.getName()))
        );
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public Page<UsuarioResponseDTO> listarUsuariosPaginados(int pagina, int tamanho) {
        return usuarioRepository.findAll(PageRequest.of(pagina, tamanho))
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class));
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = modelMapper.map(usuarioRequestDTO, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(usuarioRequestDTO.getSenha()));
        try {
            usuario = usuarioRepository.save(usuario);
            return modelMapper.map(usuario, UsuarioResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new IntegridadeDeDadosException("Erro ao salvar usuário. Verifique se o nome de usuário já existe.");
        } catch (Exception e) {
            throw new ErroAoSalvarObjetoException("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = buscarUsuarioPorId(id);
        atualizarUsuario(usuario, usuarioRequestDTO);
        try {
            usuario = usuarioRepository.save(usuario);
            return modelMapper.map(usuario, UsuarioResponseDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new IntegridadeDeDadosException("Erro ao atualizar usuário. Verifique se o nome de usuário já existe.");
        } catch (Exception e) {
            throw new ErroAoSalvarObjetoException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        buscarPorId(id);
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IntegridadeDeDadosException("Erro ao deletar usuário. Verifique se o usuário possui registros associados.");
        } catch (Exception e) {
            throw new ErroAoSalvarObjetoException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    private Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado! Id: %d, Tipo: %s".formatted(id, Usuario.class.getName()))
        );
    }

    private void atualizarUsuario(Usuario usuario, UsuarioRequestDTO usuarioRequestDTO) {
        usuario.setNomeUsuario(usuarioRequestDTO.getNomeUsuario());
        usuario.setSenha(passwordEncoder.encode(usuarioRequestDTO.getSenha()));
        usuario.setPerfis(usuarioRequestDTO.getPerfis());
    }

}
