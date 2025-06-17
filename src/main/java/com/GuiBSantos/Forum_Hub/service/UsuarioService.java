package com.GuiBSantos.Forum_Hub.service;

import com.GuiBSantos.Forum_Hub.domain.perfil.Perfil;
import com.GuiBSantos.Forum_Hub.domain.perfil.TipoUsuario;
import com.GuiBSantos.Forum_Hub.domain.usuario.Usuario;
import com.GuiBSantos.Forum_Hub.dto.request.DadosCadastroUsuario;
import com.GuiBSantos.Forum_Hub.dto.response.DadosDetalhesUsuario;
import com.GuiBSantos.Forum_Hub.dto.response.DadosRespostasUsuario;
import com.GuiBSantos.Forum_Hub.dto.response.DadosTopicosUsuario;
import com.GuiBSantos.Forum_Hub.exception.ValidacaoException;
import com.GuiBSantos.Forum_Hub.repository.PerfilRepository;
import com.GuiBSantos.Forum_Hub.repository.RespostaRepository;
import com.GuiBSantos.Forum_Hub.repository.TopicoRepository;
import com.GuiBSantos.Forum_Hub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(DadosCadastroUsuario dados) {
        if (repository.existsByEmail(dados.email())) {
            throw new ValidacaoException("E-mail já está em uso.");
        }

        if (perfilRepository.existsByNome(dados.nomePerfil())) {
            throw new ValidacaoException("Nick já está em uso.");
        }

        var senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());

        Perfil perfil = new Perfil();
        perfil.setNome(dados.nomePerfil());
        perfil.setTipoUsuario(TipoUsuario.COMUM);
        perfilRepository.save(perfil);

        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(senhaCriptografada);
        usuario.setPerfil(perfil);

        return repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public DadosDetalhesUsuario exibirDadosPerfil(Long idUsuario, Usuario usuarioAutenticado) {
        var usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado."));

        validarUsuario(usuario, usuarioAutenticado);

        var emailCensurado = censurarEmail(usuario.getEmail());
        var senhaCensurada = censurarSenha();

        return new DadosDetalhesUsuario(
                emailCensurado,
                usuario.getNome(),
                senhaCensurada,
                usuario.getDataCriacao().toString(),
                usuario.getPerfil().getNome(),
                usuario.getPerfil().getTipoUsuario().toString()
        );
    }

    @Transactional
    public void atualizarNickname(Long idUsuario, String novoNickname, Usuario usuarioAutenticado) {
        var usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));

        validarUsuario(usuario, usuarioAutenticado);
        validarNicknameDisponivel(novoNickname, usuario.getPerfil().getId());

        usuario.getPerfil().setNome(novoNickname);
    }

    @Transactional
    public void deletarConta(Long idUsuario, String senhaAtual, Usuario usuarioAutenticado) {
        var usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado."));

        validarUsuario(usuario, usuarioAutenticado);
        validarSenha(senhaAtual, usuario);

        usuario.setAtivo(false);
        usuario.setDataInativacao(LocalDateTime.now());
        usuario.setInativadoPor(usuarioAutenticado);
    }

    @Transactional
    public void atualizarSenha(Long idUsuario, String senhaAtual, String novaSenha, Usuario usuarioAutenticado) {
        var usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado."));

        validarUsuario(usuario, usuarioAutenticado);
        validarSenha(senhaAtual, usuario);

        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    @Transactional
    public void atualizarEmail(Long idUsuario, String senhaAtual, String novoEmail, Usuario usuarioAutenticado) {
        var usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado."));

        validarUsuario(usuario, usuarioAutenticado);
        validarSenha(senhaAtual, usuario);

        if (repository.existsByEmail(novoEmail)) {
            throw new ValidacaoException("O email informado já está em uso.");
        }

        usuario.setEmail(novoEmail);
    }

    public Page<DadosTopicosUsuario> listarTopicosPorUsuario(Long idUsuario, Pageable pageable) {
        return topicoRepository.findAllByAutorIdAndAtivoTrue(idUsuario, pageable)
                .map(DadosTopicosUsuario::new);
    }

    public Page<DadosRespostasUsuario> listarRespostasPorUsuario(Long idUsuario, Pageable pageable) {
        return respostaRepository.findAllByAutorIdAndAtivoTrue(idUsuario, pageable)
                .map(DadosRespostasUsuario::new);
    }

    public Usuario buscarPorId(Long idAutor) {
        return repository.findById(idAutor)
                .orElseThrow(() -> new ValidacaoException("Id do autor informado não existe!"));
    }

    public Usuario buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado com nome: " + nome));
    }

    public static Long getIdUsuarioLogado() {
        var usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario.getId();
    }

    public static Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void validarNicknameDisponivel(String nickname, Long idPerfilAtual) {
        var perfilComMesmoNick = perfilRepository.findByNomeIgnoreCase(nickname);

        if (perfilComMesmoNick.isPresent() && !perfilComMesmoNick.get().getId().equals(idPerfilAtual)) {
            throw new ValidacaoException("Este nickname já está em uso.");
        }
    }

    private void validarSenha(String senhaInformada, Usuario usuario) {
        if (!passwordEncoder.matches(senhaInformada, usuario.getSenha())) {
            throw new ValidacaoException("Senha incorreta.");
        }
    }

    private void validarUsuario(Usuario donoDoRecurso, Usuario usuarioAutenticado) {
        boolean isAdmin = usuarioAutenticado.getPerfil() != null
                && usuarioAutenticado.getPerfil().getTipoUsuario() == TipoUsuario.ADMIN;
        boolean isProprietario = donoDoRecurso.getId().equals(usuarioAutenticado.getId());

        if (!isAdmin && !isProprietario) {
            throw new ValidacaoException("Você não tem permissão para executar essa ação.");
        }
    }

    private String censurarEmail(String email) {
        int indexArroba = email.indexOf("@");
        if (indexArroba <= 0) return "**********";

        return email.charAt(0) + "**********" + email.substring(indexArroba);
    }

    private String censurarSenha() {
        return "**********";
    }
}
