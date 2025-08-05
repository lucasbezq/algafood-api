package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.openapi.GrupoControllerOpenApi;
import com.algaworks.algafood.api.converter.GrupoConverter;
import com.algaworks.algafood.api.converter.GrupoDTOConverter;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.request.GrupoRequest;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoDTOConverter grupoDTOConverter;

    @Autowired
    private GrupoConverter grupoConverter;

    @GetMapping
    public List<GrupoDTO> listar() {
        var grupos = grupoRepository.findAll();
        return grupoDTOConverter.toCollectionDTO(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable("grupoId") Long grupoId) {
        return grupoDTOConverter.toDTO(cadastroGrupoService.buscarGrupo(grupoId));
    }

    @PostMapping
    public GrupoDTO adicionar(@RequestBody @Valid GrupoRequest grupoRequest) {
        var grupo = grupoConverter.toDomain(grupoRequest);
        return grupoDTOConverter.toDTO(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable("grupoId") Long grupoId, @RequestBody @Valid GrupoRequest grupoRequest) {
        var grupoAtual = cadastroGrupoService.buscarGrupo(grupoId);
        grupoConverter.copyToDomain(grupoRequest, grupoAtual);
        return grupoDTOConverter.toDTO(cadastroGrupoService.salvar(grupoAtual));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("grupoId") Long grupoId) {
        cadastroGrupoService.excluir(grupoId);
    }
}
