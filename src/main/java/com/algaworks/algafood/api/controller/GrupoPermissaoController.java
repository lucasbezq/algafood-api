package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.converter.PermissaoDTOConverter;
import com.algaworks.algafood.api.dto.PermissaoDTO;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private PermissaoDTOConverter permissaoDTOConverter;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
        var grupo = cadastroGrupoService.buscarGrupo(grupoId);
        return permissaoDTOConverter.toCollectionDTO(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.associarPermissao(permissaoId, grupoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociarPermissao(permissaoId, grupoId);
    }
}
