package com.GuiBSantos.Forum_Hub.dto.response;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(

        @NotBlank(message = "O e-mail não pode estar em branco.")
        String email,

        @NotBlank(message = "A senha não pode estar em branco.")
        String senha
        ) {
}
