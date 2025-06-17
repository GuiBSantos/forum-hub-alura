package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosListagemUsuario(

        String nome,
        String nick,
        LocalDateTime membroDesde,
        String cargo
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getNome(), usuario.getPerfil().getNome(), usuario.getDataCriacao(), usuario.getPerfil().getTipoUsuario().toString());
    }
}
