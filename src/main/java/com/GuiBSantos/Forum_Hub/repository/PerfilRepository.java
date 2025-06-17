package com.GuiBSantos.Forum_Hub.repository;

import com.GuiBSantos.Forum_Hub.domain.perfil.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    boolean existsByNome(@NotBlank(message = "Nick é obrigatório") @Size(min = 2, max = 50) String nome);

    boolean existsByNomeIgnoreCase(String nickname);

    Optional<Perfil> findByNomeIgnoreCase(String nome);
}
