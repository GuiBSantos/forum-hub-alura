package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DadosRespostasUsuario(
        Long id,
        String mensagem,
        String autor,
        String solucao,
        LocalDateTime dataCriacao
) {
    public DadosRespostasUsuario(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getAutor().getNome(),
                resposta.getSolucao().toString(),
                resposta.getDataCriacao()
        );
    }
}
