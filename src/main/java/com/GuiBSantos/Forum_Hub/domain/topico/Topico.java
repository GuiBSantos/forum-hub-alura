package com.GuiBSantos.Forum_Hub.domain.topico;

import com.GuiBSantos.Forum_Hub.domain.curso.Curso;
import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do tópico é obrigatório.")
    @Size(min = 5, max = 100, message = "O título deve ter entre {min} e {max} caracteres.")
    private String titulo;

    @NotBlank(message = "A mensagem do tópico é obrigatória.")
    @Size(min = 10, message = "A mensagem deve ter no mínimo {min} caracteres.")
    private String mensagem;

    @NotNull(message = "A data de criação é obrigatória.")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status do tópico deve ser informado.")
    private Status status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    @NotNull(message = "O autor do tópico deve ser informado.")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    @NotNull(message = "O curso é obrigatório.")
    private Curso curso;

    @Transient
    private String cursoNome;

    @Column(nullable = false)
    private Boolean ativo = true;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "data_inativacao")
    private LocalDateTime dataInativacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inativado_por")
    private Usuario inativadoPor;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(String titulo, String mensagem, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.AGUARDANDO_RESPOSTA;
    }
}
