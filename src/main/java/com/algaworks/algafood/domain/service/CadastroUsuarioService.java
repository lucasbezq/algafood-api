package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaIncorretaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    
    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);
        var usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuario cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        var usuarioAtual = buscarUsuario(id);

        if (usuarioAtual.senhaNaoCoincideCom(senhaAtual)) throw new SenhaIncorretaException();
        usuarioAtual.setSenha(novaSenha);
    }

    @Transactional
    public void associarAoGrupo(Long usuarioId, Long grupoId) {
        var usuario = buscarUsuario(usuarioId);
        var grupo = cadastroGrupoService.buscarGrupo(grupoId);

        usuario.associar(grupo);
    }

    @Transactional
    public void desassociarAoGrupo(Long usuarioId, Long grupoId) {
        var usuario = buscarUsuario(usuarioId);
        var grupo = cadastroGrupoService.buscarGrupo(grupoId);

        usuario.desassociar(grupo);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

}
