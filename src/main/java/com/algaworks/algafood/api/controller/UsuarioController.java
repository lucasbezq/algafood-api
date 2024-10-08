package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.UsuarioConverter;
import com.algaworks.algafood.api.converter.UsuarioDTOConverter;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.request.UsuarioAtualizacaoRequest;
import com.algaworks.algafood.api.dto.request.UsuarioRequest;
import com.algaworks.algafood.api.dto.request.UsuarioSenhaRequest;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaIncorretaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import com.algaworks.algafood.domain.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioDTOConverter usuarioDTOConverter;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @GetMapping
    private List<UsuarioDTO> listar() {
        var usuarios = usuarioRepository.findAll();
        return usuarioDTOConverter.toCollection(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        var usuario =  cadastroUsuarioService.buscarUsuario(usuarioId);
        return usuarioDTOConverter.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var usuario = usuarioConverter.toDomain(usuarioRequest);
        return usuarioDTOConverter.toDTO(cadastroUsuarioService.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
                                @RequestBody @Valid UsuarioAtualizacaoRequest usuarioAtualizacaoRequest) {
        try {
            var usuarioAtual = cadastroUsuarioService.buscarUsuario(usuarioId);
            usuarioConverter.copyToDomain(usuarioAtualizacaoRequest, usuarioAtual);
            return usuarioDTOConverter.toDTO(cadastroUsuarioService.salvar(usuarioAtual));
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSenhaRequest usuarioSenhaRequest) {
        cadastroUsuarioService.alterarSenha(usuarioId, usuarioSenhaRequest.getSenhaAtual(), usuarioSenhaRequest.getNovaSenha());
    }

}
