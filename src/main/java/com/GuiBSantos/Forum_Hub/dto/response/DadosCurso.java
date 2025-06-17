package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.curso.Categoria;
import com.GuiBSantos.Forum_Hub.domain.curso.Curso;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCurso(

        @NotNull(message = "Id é obrigatório")
        Long idCurso,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Categoria é obrigatória")
        Categoria categoria
) {
        public DadosCurso(@NotNull(message = "O curso relacionado ao tópico deve ser informado.") Curso curso) {
                this(curso.getId(), curso.getNome(), curso.getCategoria());
        }
}
