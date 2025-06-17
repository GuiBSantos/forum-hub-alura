package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DadosDeletarConta(
        @NotBlank(message = "Senha é obrigatória")
        String senhaAtual
) { }
