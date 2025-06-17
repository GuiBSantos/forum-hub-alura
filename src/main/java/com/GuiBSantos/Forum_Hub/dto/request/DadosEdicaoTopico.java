package com.GuiBSantos.Forum_Hub.dto.request;

import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;

public record DadosEdicaoTopico(
        @NotBlank(message = "Titulo é obrigatório")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem
) {
    public DadosEdicaoTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensagem());
    }
}
