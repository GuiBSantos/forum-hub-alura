package com.GuiBSantos.Forum_Hub.repository;

import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {


    Page<Resposta> findAllByTopicoIdAndAtivoTrue(Long topicoId, Pageable pageable);

    Page<Resposta> findAllByAutorIdAndAtivoTrue(Long autorId, Pageable pageable);

    Page<Resposta> findAllByAtivoTrue(Pageable pageable);

    Optional<Resposta> findByIdAndAtivoTrue(Long id);

}
