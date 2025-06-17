package com.GuiBSantos.Forum_Hub.controller;

import com.GuiBSantos.Forum_Hub.config.security.DadosTokenJWT;
import com.GuiBSantos.Forum_Hub.config.security.TokenService;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosCadastroUsuario;
import com.GuiBSantos.Forum_Hub.dto.response.DadosLogin;
import com.GuiBSantos.Forum_Hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLogin dadosLogin) {
        var autenticationToken = new UsernamePasswordAuthenticationToken(dadosLogin.email(), dadosLogin.senha());
        var authentication = manager.authenticate(autenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        usuarioService.registrarUsuario(dados);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}
