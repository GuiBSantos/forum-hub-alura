package com.GuiBSantos.Forum_Hub.dto.request;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100)
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6)
        String senha,

        @NotBlank(message = "Nick é obrigatório")
        @Size(min = 2, max = 50)
        String nomePerfil
) {
    public DadosCadastroUsuario(Usuario usuario) {
        this(usuario.getNome(), usuario.getEmail(), usuario.getPassword(), usuario.getPerfil().getNome());
    }
}
