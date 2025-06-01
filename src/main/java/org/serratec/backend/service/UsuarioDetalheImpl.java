package org.serratec.backend.service;

import org.serratec.backend.entity.Usuario;
import org.serratec.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalheImpl implements UserDetailsService {

    private UsuarioRepository repository;

    //Injeção de dependência do repositório de usuários
    public UsuarioDetalheImpl(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException{
        Usuario usuario = repository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Email não encontrado"));
        return usuario;
    }
}
