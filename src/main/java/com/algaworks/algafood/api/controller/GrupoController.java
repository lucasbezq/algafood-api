package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.GrupoConverter;
import com.algaworks.algafood.api.converter.GrupoDTOConverter;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.request.GrupoRequest;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastrarGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastrarGrupoService cadastrarGrupoService;

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
        return grupoDTOConverter.toDTO(cadastrarGrupoService.buscarGrupo(grupoId));
    }

    @PostMapping
    public GrupoDTO adicionar(@RequestBody @Valid GrupoRequest grupoRequest) {
        var grupo = grupoConverter.toDomain(grupoRequest);
        return grupoDTOConverter.toDTO(cadastrarGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable("grupoId") Long grupoId, @RequestBody @Valid GrupoRequest grupoRequest) {
        var grupoAtual = cadastrarGrupoService.buscarGrupo(grupoId);
        grupoConverter.copyToDomain(grupoRequest, grupoAtual);
        return grupoDTOConverter.toDTO(cadastrarGrupoService.salvar(grupoAtual));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("grupoId") Long grupoId) {
        cadastrarGrupoService.excluir(grupoId);
    }
}
