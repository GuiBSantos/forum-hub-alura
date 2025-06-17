package com.GuiBSantos.Forum_Hub.dto.request;

import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTopico(

        @NotBlank(message = "Titulo é obrigatório")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,

        @NotNull(message = "Categoria do curso é obrigatória")
        String categoria,

        @NotNull(message = "Nome do curso é obrigatório")
        String curso
) {
        public DadosCadastroTopico(Topico topico) {
                this(topico.getTitulo(), topico.getMensagem(), topico.getCurso().getCategoria().toString(), topico.getCurso().getNome());
        }
}
