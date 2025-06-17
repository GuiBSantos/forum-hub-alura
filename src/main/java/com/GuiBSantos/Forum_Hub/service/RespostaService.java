package com.GuiBSantos.Forum_Hub.service;

import com.GuiBSantos.Forum_Hub.domain.perfil.TipoUsuario;
import com.GuiBSantos.Forum_Hub.domain.resposta.Resposta;
import com.GuiBSantos.Forum_Hub.domain.topico.Status;
import com.GuiBSantos.Forum_Hub.domain.topico.Topico;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosCadastroResposta;
import com.GuiBSantos.Forum_Hub.dto.request.DadosEdicaoResposta;
import com.GuiBSantos.Forum_Hub.dto.response.DadosDetalhesResposta;
import com.GuiBSantos.Forum_Hub.exception.ValidacaoException;
import com.GuiBSantos.Forum_Hub.repository.RespostaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoService topicoService;

    @Transactional
    public DadosDetalhesResposta cadastrarResposta(Long idTopico, DadosCadastroResposta dados, Usuario autor) {
        Topico topico = topicoService.buscarTopicoAtivoPorId(idTopico);

        if (topico.getStatus() != Status.AGUARDANDO_RESPOSTA) {
            throw new ValidacaoException("O tópico está fechado e não aceita novas respostas.");
        }

        Resposta resposta = new Resposta(dados.mensagem(), autor, topico);
        respostaRepository.save(resposta);

        return new DadosDetalhesResposta(resposta);
    }

    public DadosDetalhesResposta buscarPorId(Long id) {
        Resposta resposta = respostaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada ou inativa."));
        return new DadosDetalhesResposta(resposta);
    }

    @Transactional
    public DadosDetalhesResposta editarResposta(Long id, DadosEdicaoResposta dados, Usuario autor) {
        Resposta resposta = respostaRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada ou inativa."));

        validarPermissaoResposta(resposta, autor);

        resposta.setMensagem(dados.mensagem());
        resposta.setDataAtualizacao(LocalDateTime.now());
        return new DadosDetalhesResposta(resposta);
    }

    @Transactional
    public void deletarResposta(Long id, Usuario autor) {

        Resposta resposta = buscarRespostaAtivaPorId(id);

       validarPermissaoResposta(resposta, autor);

        resposta.setAtivo(false);
        resposta.setInativadoPor(autor);
        resposta.setDataInativacao(java.time.LocalDateTime.now());

    }

    private Resposta buscarRespostaAtivaPorId(Long idResposta) {
        return respostaRepository.findByIdAndAtivoTrue(idResposta)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada ou inativa."));
    }

    private void validarPermissaoResposta(Resposta resposta, Usuario usuarioAutenticado) {
        boolean isAdmin = usuarioAutenticado.getPerfil() != null
                && usuarioAutenticado.getPerfil().getTipoUsuario() == TipoUsuario.ADMIN;
        boolean isAutor = resposta.getAutor().getId().equals(usuarioAutenticado.getId());

        if (!isAdmin && !isAutor) {
            throw new ValidacaoException("Você não tem permissão para isso.");
        }
    }

    public Page<DadosDetalhesResposta> listarTodas(Pageable pageable) {
        return respostaRepository.findAllByAtivoTrue(pageable).map(DadosDetalhesResposta::new);
    }

    public Page<DadosDetalhesResposta> listarPorTopico(Long topicoId, Pageable pageable) {
        return respostaRepository.findAllByTopicoIdAndAtivoTrue(topicoId, pageable)
                .map(DadosDetalhesResposta::new);
    }

    public Page<DadosDetalhesResposta> listarPorAutor(Long autorId, Pageable pageable) {
        return respostaRepository.findAllByAutorIdAndAtivoTrue(autorId, pageable)
                .map(DadosDetalhesResposta::new);
    }
}
