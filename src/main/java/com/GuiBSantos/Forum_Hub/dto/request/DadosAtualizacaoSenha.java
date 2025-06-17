package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoSenha(

        @NotBlank(message = "Senha é obrigatória")
        String senhaAtual,

        @NotBlank(message = "A nova senha é obrigatória")
        String novaSenha
) { }
