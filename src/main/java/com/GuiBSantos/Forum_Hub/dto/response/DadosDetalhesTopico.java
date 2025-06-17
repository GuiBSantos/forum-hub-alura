package com.GuiBSantos.Forum_Hub.dto.response;

import com.GuiBSantos.Forum_Hub.domain.topico.Status;
import com.GuiBSantos.Forum_Hub.domain.topico.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhesTopico(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        Status status,
        String autor,
        String curso,
        String categoria,
        Integer quantidadeRespostas,
        List<DadosDetalhesResposta> respostas
) {
    public DadosDetalhesTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getDataAtualizacao(),
                topico.getStatus(),
                topico.getAutor().getPerfil().getNome(),
                topico.getCurso().getNome(),
                topico.getCurso().getCategoria().name(),
                calcularQuantidadeRespostasAtivas(topico),
                mapearRespostasAtivas(topico)
        );
    }

    private static int calcularQuantidadeRespostasAtivas(Topico topico) {
        if (topico.getRespostas() == null) {
            return 0;
        }
        return (int) topico.getRespostas().stream()
                .filter(resposta -> resposta.getAtivo() != null && resposta.getAtivo())
                .count();
    }

    private static List<DadosDetalhesResposta> mapearRespostasAtivas(Topico topico) {
        if (topico.getRespostas() == null) {
            return List.of();
        }
        return topico.getRespostas().stream()
                .filter(resposta -> resposta.getAtivo() != null && resposta.getAtivo())
                .map(DadosDetalhesResposta::new)
                .toList();
    }
}
