package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record DadosResposta(

        @NotNull(message = "Id da resposta é obrigatório.")
        Long idResposta,

        @NotBlank(message = "Mensagem da resposta é obrigatória.")
        @Size(min = 5, message = "A mensagem deve ter no mínimo {min} caracteres.")
        String mensagem,

        @NotBlank(message = "O título do tópico é obrigatório.")
        String nomeTopico,

        @NotNull(message = "Data de criação é obrigatória.")
        LocalDateTime dataCriacao,

        @NotBlank(message = "Nome do autor é obrigatório.")
        String nomeAutor
) {
    public DadosResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getDataCriacao(),
                resposta.getAutor().getPerfil().getNome()
        );
    }
}
