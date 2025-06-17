package com.GuiBSantos.Forum_Hub.domain.resposta;

import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5)
    private String mensagem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    @NotNull
    private Topico topico;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    @NotNull
    private Usuario autor;

    private Boolean solucao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "data_inativacao")
    private LocalDateTime dataInativacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inativado_por")
    private Usuario inativadoPor;

    public Resposta(String mensagem, Usuario autor, Topico topico) {
        this.mensagem = mensagem;
        this.autor = autor;
        this.topico = topico;
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }
}
