package com.GuiBSantos.Forum_Hub.dto.request;

import jakarta.validation.constraints.NotNull;

public record DadosMarcarSolucao(

        @NotNull(message = "Id não pode estar em branco.")
        Long idResposta
) {}
