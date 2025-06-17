package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoNickname(
        @NotBlank(message = "O nickname não pode ser vazio.")
        @Size(min = 3, max = 50, message = "O nickname deve ter entre 3 e 50 caracteres.")
        String nickname
) {
}