package com.GuiBSantos.Forum_Hub.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record DadosPaginacao<T>(
        List<T> content,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas,
        boolean ultima,
        boolean primeira
) {
    public DadosPaginacao(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }
}
