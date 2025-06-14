package com.biblioteca.apibiblioteca.service;

import com.biblioteca.apibiblioteca.controller.dto.AuthResponse;
import com.biblioteca.apibiblioteca.controller.dto.LoginRequest;
import com.biblioteca.apibiblioteca.controller.dto.RegisterRequest;
import com.biblioteca.apibiblioteca.domain.Usuario;
import com.biblioteca.apibiblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        var usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(request.getRole());
        usuario.setAtivo(true);
        
        usuarioRepository.save(usuario);
        
        var jwtToken = jwtService.generateToken(usuario);
        return new AuthResponse(jwtToken, usuario.getEmail(), usuario.getRole().name());
    }
    
    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );
        
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        var jwtToken = jwtService.generateToken(usuario);
        return new AuthResponse(jwtToken, usuario.getEmail(), usuario.getRole().name());
    }
}