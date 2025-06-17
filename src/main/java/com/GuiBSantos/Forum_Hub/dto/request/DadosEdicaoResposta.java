package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DadosEdicaoResposta(

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem
) {}
