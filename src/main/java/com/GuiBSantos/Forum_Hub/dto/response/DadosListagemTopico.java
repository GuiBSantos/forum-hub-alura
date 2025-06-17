package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import com.GuiBSantos.Forum_Hub.domain.topico.Status;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        String autor,
        String curso,
        String categoria,
        Integer quantidadeRespostas
) {
    public DadosListagemTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getPerfil().getNome(),
                topico.getCurso().getNome(),
                topico.getCurso().getCategoria().name(),
                topico.getRespostas() != null ? topico.getRespostas().size() : 0
        );
    }
}