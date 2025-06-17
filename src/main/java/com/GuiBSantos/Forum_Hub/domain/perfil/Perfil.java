package com.GuiBSantos.Forum_Hub.domain.perfil;

import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Perfil")
@Table(name = "perfil")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do perfil não pode estar em branco.")
    @Size(min = 3, max = 50, message = "O nome do perfil deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de usuario deve ser informado.")
    private TipoUsuario tipoUsuario;

    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;

    public Perfil(@NotBlank(message = "Nick é obrigatório") @Size(min = 2, max = 50) String nomePerfil, TipoUsuario tipoUsuario) {
        this.nome = nomePerfil;
        this.tipoUsuario = tipoUsuario;
    }
}
