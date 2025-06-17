package com.GuiBSantos.Forum_Hub.repository;

import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    Optional<Usuario> findByNome(String nome);

    boolean existsByEmail(@NotBlank(message = "E-mail é obrigatório") @Email String email);

    @Query("SELECT u FROM Usuario u WHERE u.ativo = true")
    Page<Usuario> findAllAtivos(Pageable pageable);



}
