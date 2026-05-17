package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.converter.GrupoConverter;
import com.algaworks.algafood.api.converter.GrupoDTOConverter;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.request.GrupoRequest;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public CollectionModel<GrupoDTO> listar() {
        var grupos = grupoRepository.findAll();
        return grupoDTOConverter.toCollectionModel(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable("grupoId") Long grupoId) {
        return grupoDTOConverter.toModel(cadastroGrupoService.buscarGrupo(grupoId));
    }

    @PostMapping
    public GrupoDTO adicionar(@RequestBody @Valid GrupoRequest grupoRequest) {
        var grupo = grupoConverter.toDomain(grupoRequest);
        return grupoDTOConverter.toModel(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable("grupoId") Long grupoId, @RequestBody @Valid GrupoRequest grupoRequest) {
        var grupoAtual = cadastroGrupoService.buscarGrupo(grupoId);
        grupoConverter.copyToDomain(grupoRequest, grupoAtual);
        return grupoDTOConverter.toModel(cadastroGrupoService.salvar(grupoAtual));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("grupoId") Long grupoId) {
        cadastroGrupoService.excluir(grupoId);
    }
}
