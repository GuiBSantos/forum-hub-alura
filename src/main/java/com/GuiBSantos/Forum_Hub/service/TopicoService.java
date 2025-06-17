package com.GuiBSantos.Forum_Hub.service;

import com.GuiBSantos.Forum_Hub.domain.curso.Curso;
import com.GuiBSantos.Forum_Hub.domain.perfil.TipoUsuario;
import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import com.GuiBSantos.Forum_Hub.domain.topico.Status;
import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosCadastroTopico;
import com.GuiBSantos.Forum_Hub.dto.request.DadosEdicaoTopico;
import com.GuiBSantos.Forum_Hub.dto.response.DadosTopico;
import com.GuiBSantos.Forum_Hub.exception.ValidacaoException;
import com.GuiBSantos.Forum_Hub.repository.RespostaRepository;
import com.GuiBSantos.Forum_Hub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public DadosTopico cadastrarTopico(DadosCadastroTopico dadosCadastroTopico) {

        validarTopicoDuplicado(dadosCadastroTopico.titulo(), dadosCadastroTopico.mensagem());

        Usuario autor = usuarioService.getUsuarioLogado();
        Curso curso = cursoService.buscarPorNome(dadosCadastroTopico.curso());

        Topico topico = new Topico(
                dadosCadastroTopico.titulo(),
                dadosCadastroTopico.mensagem(),
                autor,
                curso
        );

        topico.setDataCriacao(LocalDateTime.now());
        topico.setCursoNome(curso.getNome());

        topicoRepository.save(topico);
        return new DadosTopico(topico);
    }

    @Transactional
    public DadosTopico editarTopico(Long idTopico, DadosEdicaoTopico dadosEdicaoTopico, Usuario usuarioAutenticado) {

        Topico topico = buscarTopicoAtivoPorId(idTopico);

        validarPermissaoTopico(topico, usuarioAutenticado);
        validarTopicoDuplicado(dadosEdicaoTopico.titulo(), dadosEdicaoTopico.mensagem());

        topico.setTitulo(dadosEdicaoTopico.titulo());
        topico.setMensagem(dadosEdicaoTopico.mensagem());
        topico.setDataAtualizacao(LocalDateTime.now());

        return new DadosTopico(topico);
    }

    @Transactional
    public void deletarTopico(Long idTopico, Usuario usuarioAutenticado) {
        Topico topico = buscarTopicoAtivoPorId(idTopico);
        validarPermissaoTopico(topico, usuarioAutenticado);
        topico.setAtivo(false);
        topico.setDataInativacao(LocalDateTime.now());
        topico.setInativadoPor(usuarioAutenticado);
        topico.setStatus(Status.EXCLUIDO);
    }

    @Transactional
    public void marcarRespostaComoSolucao(Long idTopico, Long idResposta, Usuario usuarioAutenticado) {
        Topico topico = buscarTopicoAtivoPorId(idTopico);

        validarPermissaoTopico(topico, usuarioAutenticado);

        Resposta resposta = respostaRepository.findByIdAndAtivoTrue(idResposta)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada ou inativa."));

        if (!resposta.getTopico().getId().equals(idTopico)) {
            throw new ValidacaoException("Essa resposta não pertence ao tópico.");
        }

        resposta.setSolucao(true);
        topico.setStatus(Status.RESOLVIDO);
    }

    @Transactional
    public void reabrirTopico(Long idTopico, Usuario usuarioAutenticado) {
        Topico topico = buscarTopicoAtivoPorId(idTopico);

        validarPermissaoTopico(topico, usuarioAutenticado);

        if (!topico.getStatus().equals(Status.RESOLVIDO)) {
            throw new ValidacaoException("O tópico não está fechado para ser reaberto.");
        }

        topico.getRespostas().forEach(resposta -> resposta.setSolucao(false));

        topico.setStatus(Status.AGUARDANDO_RESPOSTA);
    }

    public Topico buscarTopicoAtivoPorId(Long idTopico) {
        return topicoRepository.findByIdAndAtivoTrue(idTopico)
                .orElseThrow(() -> new ValidacaoException("Tópico não encontrado ou já inativo"));
    }

    private void validarPermissaoTopico(Topico topico, Usuario usuarioAutenticado) {
        boolean isAdmin = usuarioAutenticado.getPerfil() != null
                && usuarioAutenticado.getPerfil().getTipoUsuario() == TipoUsuario.ADMIN;
        boolean isAutor = topico.getAutor().getId().equals(usuarioAutenticado.getId());

        if (!isAdmin && !isAutor) {
            throw new ValidacaoException("Você não tem permissão para isso.");
        }
    }

    private void validarTopicoDuplicado(String titulo, String mensagem) {
        if (topicoRepository.existsByTituloAndMensagem(titulo, mensagem)) {
            throw new ValidacaoException("Já existe um tópico com o mesmo título e mensagem.");
        }
    }
}
