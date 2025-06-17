package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;

public record DadosDetalhesUsuario(
        String email,
        String nome,
        String senha,
        String dataCriacao,
        String nick,
        String cargo

) {
    public DadosDetalhesUsuario(Usuario usuario) {
        this(usuario.getUsername(), usuario.getNome(), usuario.getPassword(), usuario.getDataCriacao().toString(), usuario.getPerfil().getNome(), usuario.getPerfil().getTipoUsuario().toString());
    }
}
