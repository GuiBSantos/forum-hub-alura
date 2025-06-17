package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.topico.Status;
import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record DadosTopico(

        @NotNull(message = "Id do tópico é obrigatório.")
        Long idTopico,

        @NotBlank(message = "Título é obrigatório.")
        @Size(min = 5, message = "O título deve ter no mínimo {min} caracteres.")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória.")
        @Size(min = 10, message = "A mensagem deve ter no mínimo {min} caracteres.")
        String mensagem,

        @NotNull(message = "Data de criação é obrigatória.")
        LocalDateTime dataCriacao,

        @NotNull(message = "Status do tópico é obrigatório.")
        Status status,

        @NotNull(message = "Autor do tópico é obrigatório.")
        DadosUsuario autor,

        @NotNull(message = "Curso do tópico é obrigatório.")
        DadosCurso curso,

        @NotNull(message = "Lista de respostas não pode ser nula.")
        List<DadosResposta> respostas

) {
    public DadosTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                new DadosUsuario(topico.getAutor()),
                new DadosCurso(topico.getCurso()),
                topico.getRespostas().stream().map(DadosResposta::new).toList()
        );
    }
}
