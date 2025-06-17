package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroResposta(

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem
) {}
