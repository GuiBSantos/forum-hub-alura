package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DadosDetalhesResposta(
        String mensagem,
        String autor,
        LocalDateTime dataCriacao

) {
    public DadosDetalhesResposta(Resposta resposta) {

        this(resposta.getMensagem(), resposta.getAutor().getNome(), resposta.getDataCriacao());
    }

}
