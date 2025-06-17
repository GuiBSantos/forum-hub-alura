package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosUsuario(

        @NotNull(message = "Id é obrigatório")
        Long idAutor,

        @NotBlank(message = "Nome é obrigatório")
        String nome

) {
        public DadosUsuario(@NotNull(message = "O autor do tópico deve ser informado.") Usuario autor) {
             this(autor.getId(), autor.getNome());
        }
}
