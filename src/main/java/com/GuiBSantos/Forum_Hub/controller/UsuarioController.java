package com.GuiBSantos.Forum_Hub.controller;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosAtualizacaoEmail;
import com.GuiBSantos.Forum_Hub.dto.request.DadosAtualizacaoNickname;
import com.GuiBSantos.Forum_Hub.dto.request.DadosAtualizacaoSenha;
import com.GuiBSantos.Forum_Hub.dto.request.DadosDeletarConta;
import com.GuiBSantos.Forum_Hub.dto.response.*;
import com.GuiBSantos.Forum_Hub.repository.UsuarioRepository;
import com.GuiBSantos.Forum_Hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // PUT

    @GetMapping
    public ResponseEntity<DadosPaginacao<DadosListagemUsuario>> listar(
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {

        var page = usuarioRepository.findAllAtivos(pageable)
                .map(DadosListagemUsuario::new);

        return ResponseEntity.ok(new DadosPaginacao<>(page));

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> exibirPerfilPorId(@PathVariable Long id) {

        var user = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(new DadosListagemUsuario(user));
    }

    @GetMapping("/{id}/topicos")
    public ResponseEntity<DadosPaginacao<DadosTopicosUsuario>> listarTopicos(
            @PathVariable Long id,
            Pageable pageable) {
        var page = usuarioService.listarTopicosPorUsuario(id, pageable);
        return ResponseEntity.ok(new DadosPaginacao<>(page));
    }

    @GetMapping("/{id}/respostas")
    public ResponseEntity<DadosPaginacao<DadosRespostasUsuario>> listarRespostas(
            @PathVariable Long id,
            Pageable pageable) {
        var page = usuarioService.listarRespostasPorUsuario(id, pageable);
        return ResponseEntity.ok(new DadosPaginacao<>(page));
    }

    @GetMapping("/{id}/config/perfil")
    public ResponseEntity<DadosDetalhesUsuario> exibirDadosPerfil(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ) {
        var dados = usuarioService.exibirDadosPerfil(id, usuarioAutenticado);
        return ResponseEntity.ok(dados);
    }

    // PUT

    @PutMapping("/{id}/nickname")
    @Transactional
    public ResponseEntity<Void> atualizarNickname(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoNickname dados,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {
        usuarioService.atualizarNickname(id, dados.nickname(), usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/config/perfil/email")
    public ResponseEntity<Void> atualizarEmail(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoEmail dados,
            @AuthenticationPrincipal Usuario usuario) {

        usuarioService.atualizarEmail(id, dados.senhaAtual(), dados.novoEmail(), usuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/config/perfil/senha")
    public ResponseEntity<Void> atualizarSenha(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoSenha dados,
            @AuthenticationPrincipal Usuario usuario) {

        usuarioService.atualizarSenha(id, dados.senhaAtual(), dados.novaSenha(), usuario);
        return ResponseEntity.noContent().build();
    }

    // DELETE

    @DeleteMapping("/{id}/config/perfil")
    public ResponseEntity<Void> deletarConta(
            @PathVariable Long id,
            @RequestBody @Valid DadosDeletarConta dados,
            @AuthenticationPrincipal Usuario usuario) {

        usuarioService.deletarConta(id, dados.senhaAtual(), usuario);
        return ResponseEntity.noContent().build();
    }
}
