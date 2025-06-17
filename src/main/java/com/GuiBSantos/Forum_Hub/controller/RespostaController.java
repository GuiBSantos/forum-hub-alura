package com.GuiBSantos.Forum_Hub.controller;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosCadastroResposta;
import com.GuiBSantos.Forum_Hub.dto.request.DadosEdicaoResposta;
import com.GuiBSantos.Forum_Hub.dto.response.DadosDetalhesResposta;
import com.GuiBSantos.Forum_Hub.dto.response.DadosPaginacao;
import com.GuiBSantos.Forum_Hub.service.RespostaService;
import com.GuiBSantos.Forum_Hub.service.TopicoService;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/topicos/{topicoId}/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private TopicoService topicoService;

    // POST

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhesResposta> cadastrar(
            @PathVariable Long topicoId,
            @RequestBody @Valid DadosCadastroResposta dados,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        var resposta = respostaService.cadastrarResposta(topicoId, dados, usuarioAutenticado);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/{id}/solucao")
    @Transactional
    public ResponseEntity<Void> marcarComoSolucao(
            @PathVariable Long topicoId,
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        topicoService.marcarRespostaComoSolucao(topicoId, id, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }

    // GET

    // Query param | sort, size, page, data


    @GetMapping
    public ResponseEntity<DadosPaginacao<DadosDetalhesResposta>> listar(
            @PathVariable Long topicoId,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {

        var page = respostaService.listarPorTopico(topicoId, pageable);
        return ResponseEntity.ok(new DadosPaginacao<>(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesResposta> detalhesResposta(@PathVariable Long id) {
        var dto = respostaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // PUT

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhesResposta> editar(
            @PathVariable Long id,
            @RequestBody @Valid DadosEdicaoResposta dados,
            @AuthenticationPrincipal Usuario usuario) {

        var dto = respostaService.editarResposta(id, dados, usuario);
        return ResponseEntity.ok(dto);
    }

    // DELETE

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario) {

        respostaService.deletarResposta(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
