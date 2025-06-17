package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosTopicosUsuario(
        Long id,
        String titulo,
        String mensagem,
        String categoria,
        String status,
        LocalDateTime dataCriacao
) {
    public DadosTopicosUsuario(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getCurso().getNome(),
                topico.getStatus().name(),
                topico.getDataCriacao()
        );
    }


}
