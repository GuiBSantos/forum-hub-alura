package com.GuiBSantos.Forum_Hub.service;

import com.GuiBSantos.Forum_Hub.domain.curso.Categoria;
import com.GuiBSantos.Forum_Hub.domain.curso.Curso;
import com.GuiBSantos.Forum_Hub.exception.ValidacaoException;
import com.GuiBSantos.Forum_Hub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    public Curso buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .orElseThrow(() -> new ValidacaoException("Curso não encontrado com nome: " + nome));
    }

    public Curso buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Curso com ID " + id + " não encontrado."));
    }

    public Curso buscarPorCategoria(String categoriaStr) {
        Categoria categoriaEnum;
        try {
            categoriaEnum = Categoria.valueOf(categoriaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidacaoException("Categoria inválida: " + categoriaStr);
        }

        return repository.findByCategoria(categoriaEnum)
                .orElseThrow(() -> new ValidacaoException("Nenhum curso encontrado para a categoria: " + categoriaEnum));
    }

}
