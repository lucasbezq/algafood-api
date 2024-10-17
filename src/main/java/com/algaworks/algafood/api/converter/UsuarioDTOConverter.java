package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toDTO(usuario))
                .collect(Collectors.toList());
    }
}
