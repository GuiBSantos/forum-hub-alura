package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoEmail(

        @NotBlank(message = "Senha é obrigatória")
        String senhaAtual,

        @NotBlank(message = "Email é obrigatório")
        String novoEmail
) { }
