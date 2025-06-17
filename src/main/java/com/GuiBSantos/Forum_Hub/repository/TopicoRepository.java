package com.GuiBSantos.Forum_Hub.repository;

import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(@NotBlank(message = "Titulo é obrigatório") String titulo, @NotBlank(message = "Mensagem é obrigatória") String mensagem);

    Optional<Topico> findByIdAndAtivoTrue(Long idTopico);

    @Query("SELECT t FROM Topico t WHERE t.ativo = true")
    Page<Topico> findAllAtivos(Pageable pageable);

    Page<Topico> findAllByTituloContainingAndAtivoTrue(String titulo, Pageable pageable);

    Page<Topico> findAllByAutorIdAndAtivoTrue(Long autorId, Pageable pageable);

    boolean existsByTituloAndMensagemAndIdNot(@NotBlank(message = "Titulo é obrigatório") String titulo, @NotBlank(message = "Mensagem é obrigatória") String mensagem, Long idTopico);
}
