package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.GrupoDTOConverter;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoDTOConverter grupoDTOConverter;

    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long usuarioId) {
        var usuario = cadastroUsuarioService.buscarUsuario(usuarioId);
        var grupos = usuario.getGrupos();
        return grupoDTOConverter.toCollectionDTO(grupos);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associarAoGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarAoGrupo(usuarioId, grupoId);
    }

}
