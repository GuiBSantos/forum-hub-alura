package com.GuiBSantos.Forum_Hub.repository;

import com.GuiBSantos.Forum_Hub.domain.curso.Categoria;
import com.GuiBSantos.Forum_Hub.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByNome(String nome);

    Optional<Curso> findByCategoria(Categoria categoria);
}
