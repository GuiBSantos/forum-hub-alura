package com.GuiBSantos.Forum_Hub.controller;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosCadastroTopico;
import com.GuiBSantos.Forum_Hub.dto.request.DadosEdicaoTopico;
import com.GuiBSantos.Forum_Hub.dto.response.DadosDetalhesTopico;
import com.GuiBSantos.Forum_Hub.dto.response.DadosListagemTopico;
import com.GuiBSantos.Forum_Hub.dto.response.DadosPaginacao;
import com.GuiBSantos.Forum_Hub.dto.response.DadosTopico;
import com.GuiBSantos.Forum_Hub.repository.TopicoRepository;
import com.GuiBSantos.Forum_Hub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;


    @Autowired
    private TopicoService topicoService;


    // POST

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTopico(@RequestBody @Valid DadosCadastroTopico dadosCadastroTopico) {
        var dto = topicoService.cadastrarTopico(dadosCadastroTopico);
        return ResponseEntity.ok(dto);
    }

    // GET

    // Query param | sort, size, page, data, status, categoria

    @GetMapping
    public ResponseEntity<DadosPaginacao<DadosListagemTopico>> listar(
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {

        var page = topicoRepository.findAllAtivos(pageable)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(new DadosPaginacao<>(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesTopico> detalhesTopico(@PathVariable Long id) {
        var topico = topicoService.buscarTopicoAtivoPorId(id);
        return ResponseEntity.ok(new DadosDetalhesTopico(topico));
    }

    @GetMapping("/buscar")
    public ResponseEntity<DadosPaginacao<DadosListagemTopico>> buscarPorTitulo(
            @RequestParam String titulo,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        var page = topicoRepository.findAllByTituloContainingAndAtivoTrue(titulo, pageable)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(new DadosPaginacao<>(page));
    }

    // DELETE

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        topicoService.deletarTopico(id, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }


    // PUT

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosTopico> editarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DadosEdicaoTopico dados,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        var dto = topicoService.editarTopico(id, dados, usuarioAutenticado);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/reabrir")
    @Transactional
    public ResponseEntity<Void> reabrirTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        topicoService.reabrirTopico(id, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }

}
