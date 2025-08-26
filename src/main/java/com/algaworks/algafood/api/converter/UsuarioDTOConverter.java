package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioDTOConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTOConverter() {
        super(Usuario.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        var usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

        usuarioDTO.add(linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioDTO.getId()))
                .withSelfRel());

        usuarioDTO.add(linkTo(methodOn(UsuarioController.class)
                .listar()).withRel("usuarios"));

        return usuarioDTO;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
