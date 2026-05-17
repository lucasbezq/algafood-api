package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.GrupoDTOConverter;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoDTOConverter grupoDTOConverter;

    @GetMapping
    public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
        var usuario = cadastroUsuarioService.buscarUsuario(usuarioId);
        var grupos = usuario.getGrupos();
        var gruposDTO = grupoDTOConverter.toCollectionModel(grupos);

        gruposDTO.forEach(grupoDTO -> {
            grupoDTO.removeLinks();

            grupoDTO.add(
                    linkTo(methodOn(GrupoController.class)
                            .buscar(grupoDTO.getId()))
                            .withSelfRel()
            );

            grupoDTO.add(
                    linkTo(methodOn(GrupoController.class)
                            .listar())
                            .withRel("grupos")
            );

            grupoDTO.add(
                    linkTo(methodOn(GrupoPermissaoController.class)
                            .listar(grupoDTO.getId()))
                            .withRel("permissoes")
            );
        });




        return gruposDTO;
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
