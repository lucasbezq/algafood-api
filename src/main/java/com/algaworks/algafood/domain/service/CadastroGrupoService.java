package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.algaworks.algafood.domain.util.Constants.MSG_GRUPO_EM_USO;

@Service
public class CadastroGrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    @Transactional
    public void associarPermissao(Long permissaoId, Long grupoId) {
        var grupo = buscarGrupo(grupoId);
        var permissao = cadastroPermissaoService.buscarPermissao(permissaoId);
        grupo.associar(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long permissaoId, Long grupoId) {
        var grupo = buscarGrupo(grupoId);
        var permissao = cadastroPermissaoService.buscarPermissao(permissaoId);
        grupo.desassociar(permissao);
    }

    public Grupo buscarGrupo(Long id) {
        return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }
}
